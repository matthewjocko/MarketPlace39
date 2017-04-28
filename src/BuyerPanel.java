
/**
 * Adam Kummer
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class BuyerPanel extends JPanel {

    private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

    private static final String ADD_BUTTON_LABEL = "Add >>";

    private static final String PROCEED_TO_CHECKOUT_CHECKOUT = "ProceedToCheckout";

    private static final String REMOVE_BUTTON_LABEL = "<< Remove";

    private static final String DEFAULT_SOURCE_CHOICE_LABEL = "Market";

    private static final String DEFAULT_DEST_CHOICE_LABEL = "Cart";

    private static String[][] inventory;
    private static String[] inputData;
    private static File inventoryFilePath;
    private static final String LINETAG = "<Line/>";
    private static final String COLTAG = "<Col/>";

    private JLabel sourceLabel;

    private JList sourceList;

    private SortedListModel sourceListModel;

    private JList destList;

    private SortedListModel destListModel;

    private JLabel destLabel;

    private JButton addButton;

    private JButton removeButton;

    public BuyerPanel() {
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridBagLayout());
        sourceLabel = new JLabel(DEFAULT_SOURCE_CHOICE_LABEL);
        sourceListModel = new SortedListModel();
        sourceList = new JList(sourceListModel);
        add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 5, .5,
                1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                EMPTY_INSETS, 0, 0));

        addButton = new JButton(ADD_BUTTON_LABEL);
        add(addButton, new GridBagConstraints(1, 2, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        addButton.addActionListener(new AddListener());


        removeButton = new JButton(REMOVE_BUTTON_LABEL);
        add(removeButton, new GridBagConstraints(1, 4, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                0, 5, 0, 5), 0, 0));
        removeButton.addActionListener(new RemoveListener());

        destLabel = new JLabel(DEFAULT_DEST_CHOICE_LABEL);
        destListModel = new SortedListModel();
        destList = new JList(destListModel);
        add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        add(new JScrollPane(destList), new GridBagConstraints(2, 1, 1, 5, .5,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                EMPTY_INSETS, 0, 0));

    }

    public String getSourceChoicesTitle() {
        return sourceLabel.getText();
    }

    public void setSourceChoicesTitle(String newValue) {
        sourceLabel.setText(newValue);
    }

    public String getDestinationChoicesTitle() {
        return destLabel.getText();
    }

    public void setDestinationChoicesTitle(String newValue) {
        destLabel.setText(newValue);
    }

    public void clearSourceListModel() {
        sourceListModel.clear();
    }

    public void clearDestinationListModel() {
        destListModel.clear();
    }

    public void addSourceElements(ListModel newValue) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(ListModel newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(ListModel newValue) {
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, ListModel newValues) {
        int size = newValues.getSize();
        for (int i = 0; i < size; i++) {
            model.add(newValues.getElementAt(i));
        }
    }

    public void addSourceElements(Object newValue[]) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(Object newValue[]) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(Object newValue[]) {
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, Object newValues[]) {
        model.addAll(newValues);
    }

    public Iterator sourceIterator() {
        return sourceListModel.iterator();
    }

    public Iterator destinationIterator() {
        return destListModel.iterator();
    }

    public void setSourceCellRenderer(ListCellRenderer newValue) {
        sourceList.setCellRenderer(newValue);
    }

    public ListCellRenderer getSourceCellRenderer() {
        return sourceList.getCellRenderer();
    }

    public void setDestinationCellRenderer(ListCellRenderer newValue) {
        destList.setCellRenderer(newValue);
    }

    public ListCellRenderer getDestinationCellRenderer() {
        return destList.getCellRenderer();
    }

    public void setVisibleRowCount(int newValue) {
        sourceList.setVisibleRowCount(newValue);
        destList.setVisibleRowCount(newValue);
    }

    public int getVisibleRowCount() {
        return sourceList.getVisibleRowCount();
    }

    public void setSelectionBackground(Color newValue) {
        sourceList.setSelectionBackground(newValue);
        destList.setSelectionBackground(newValue);
    }

    public Color getSelectionBackground() {
        return sourceList.getSelectionBackground();
    }

    public void setSelectionForeground(Color newValue) {
        sourceList.setSelectionForeground(newValue);
        destList.setSelectionForeground(newValue);
    }

    public Color getSelectionForeground() {
        return sourceList.getSelectionForeground();
    }

    private void clearSourceSelected() {
        Object selected[] = sourceList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            sourceListModel.removeElement(selected[i]);
        }
        sourceList.getSelectionModel().clearSelection();
    }

    private void clearDestinationSelected() {
        Object selected[] = destList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            destListModel.removeElement(selected[i]);
        }
        destList.getSelectionModel().clearSelection();
    }




    // Reads Inventory.txt and it into a String[]
//    public static String[] invToStringArray(String fileName) throws IOException {
//        FileReader fr = new FileReader(fileName);
//        BufferedReader br = new BufferedReader(fr);
//        List<String> lines = new ArrayList<String>();
//        String line = "";
//        int iteration = 0;
//        while ((line = br.readLine()) != null) {
//            if(iteration == 0) {
//                iteration++;
//                continue;
//            }
//
//
//
//
//
//
//            line = line.replace("<Line/>", " ");
//            line = line.replace("<Col/>", " ");
//
//
//
//            lines.add(line);
//        }
//        br.close();
//        return lines.toArray(new String[lines.size()]);
//    }

    public static void main(String args[]) {
        JFrame f = new JFrame("BuyerPanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BuyerPanel dual = new BuyerPanel();
        updateInventory();
        try {
            dual.addSourceElements(getElementsFromInv());
//            dual.addSourceElements(invToStringArray("/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Inventory.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.getContentPane().add(dual, BorderLayout.CENTER);
        f.setSize(700, 300);
        f.setVisible(true);
    }

    private static String[] getElementsFromInv() {
        String[] output = new String[inventory.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = "Name: " + inventory[i][2] + ";\nDescription: " + inventory[i][4] + ";\nPrice: " + inventory[i][3];
        }
        return output;
    }

    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object selected[] = sourceList.getSelectedValues();
            addDestinationElements(selected);
            clearSourceSelected();
        }
    }

    private class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object selected[] = destList.getSelectedValues();
            addSourceElements(selected);
            clearDestinationSelected();
        }
    }

    /**
     *  Updates the inventory 2d array with content from inventory.txt
     */
    private static void updateInventory() {
        inputData = null;
        setArray();
        fillArray();
    }

    /**
     *  Fills the inventory 2d array with the contents from the inventory.txt file
     */
    private static void fillArray() {
        for(int j = 1; j < inputData.length; j++) {
            String currentLine = inputData[j];
            String[] tmpLine = currentLine.split(COLTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                inventory[j - 1][i] = tmp;
            }
        }
    }

    /**
     *  Sets the inventory 2d array to the appropriate size
     */
    private static void setArray() {
        int rows = 0;
        int fields = 0;
        Scanner in = null;
        String content = "";
        try {
            String inventoryFilePath = "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Inventory.txt";
            in = new Scanner(new File(inventoryFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputData = content.split(LINETAG);
        rows = inputData.length - 1;
        fields = inputData[0].split(COLTAG).length;
        inventory = new String[rows][fields];
        in.close();
    }
}

class SortedListModel extends AbstractListModel {

    SortedSet model;

    public SortedListModel() {
        model = new TreeSet();
    }

    public int getSize() {
        return model.size();
    }

    public Object getElementAt(int index) {
        return model.toArray()[index];
    }

    public void add(Object element) {
        if (model.add(element)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(Object elements[]) {
        Collection c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(Object element) {
        return model.contains(element);
    }

    public Object firstElement() {
        return model.first();
    }

    public Iterator iterator() {
        return model.iterator();
    }

    public Object lastElement() {
        return model.last();
    }

    public boolean removeElement(Object element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }


}

