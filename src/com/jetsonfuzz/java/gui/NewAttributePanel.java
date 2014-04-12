/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jetsonfuzz.java.gui;

import com.jetsonfuzz.java.dw.Database;
import com.jetsonfuzz.java.dw.Warehouse;
import com.jetsonfuzz.java.main.Properties;
import com.jetsonfuzz.java.main.Util;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author dking
 */
public class NewAttributePanel extends javax.swing.JPanel {
    private Properties _props = null;
    private Database _db = null;
    private Warehouse _dw = null;
    
    private String _attrName = "";
    private int _dataType = -1;
    private int _size = 0;
    private int _precision = 0;
    private boolean _isNullable = false;
    private boolean _isPrimaryKey = false;
    private boolean _isAutoIncrement = false;
    private DefaultComboBoxModel _dataTypeModel = null;
    
    /**
     * Creates new form NewAttributePanel
     * @param props
     * @param db
     * @param dw
     */
    public NewAttributePanel(Properties props, Database db, Warehouse dw) {
        this._props = props;
        this._db = db;
        this._dw = dw;
        this._dataTypeModel = new DefaultComboBoxModel();
        
        initComponents();
        
        Map jdbcTypes = Database.getJdbcTypeName();
        
        Iterator entries = jdbcTypes.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            this._dataTypeModel.addElement(thisEntry.getValue());
        }
        
        this.cbxAttrType.setModel(_dataTypeModel);
        this.cbxAttrType.updateUI();
        
        this.txtAttrName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
            }
        });        
    }

    public boolean saveForm() {
        boolean bReturn = false;
        
        this._attrName = this.txtAttrName.getText();
        this._size = (int) this.spinSize.getValue();
        this._precision = (int) this.spinPrecision.getValue();
        this._isNullable = this.cbxNullable.isSelected();
        this._isPrimaryKey = this.cbxPrimaryKey.isSelected();
        this._isAutoIncrement = this.cbxAutoIncrement.isSelected();

        int selectedIndex = this.cbxAttrType.getSelectedIndex();
        String value = (String) this._dataTypeModel.getElementAt(selectedIndex);
        
        Iterator entries = Database.getJdbcTypeName().entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            String entryValue = (String) thisEntry.getValue();
            
            if (entryValue.compareTo(value) == 0) {
                this._dataType = (int) thisEntry.getKey();
                break;
            }
        }
                
        this._dataType = this.cbxAttrType.getSelectedIndex();
        
        return bReturn;
    }
    
    public String getAttributeName() {
        return this._attrName;
    }
    
    public void setAttributeName(String attrName) {
        this._attrName = attrName;
    }
    
    public int getDataType() {
        return this._dataType;
    }
    
    public void setDataType(int dataType) {
        this._dataType = dataType;
    }
    
    public int getAttributeSize() {
        return this._size;
    }
    
    public void setAttributeSize(int size) {
        this._size = size;
    }
    
    public int getAttributePrecision() {
        return this._precision;
    }
    
    public void setAttributePrecision(int precision) {
        this._precision = precision;
    }
    
    public boolean getNullable() {
        return this._isNullable;
    }
    
    public void setNullable(boolean allowNulls) {
        this._isNullable = allowNulls;
    }
    
    public boolean getPrimaryKey() {
        return this._isPrimaryKey;
    }
    
    public void setPrimaryKey(boolean isPrimaryKey) {
        this._isPrimaryKey = isPrimaryKey;
    }
    
    public boolean getAutoIncrement() {
        return this._isAutoIncrement;
    }
    
    public void setAutoIncrement(boolean isAutoIncrement) {
        this._isAutoIncrement = isAutoIncrement;
    }
    
    public boolean validateForm() {
        boolean bReturn = true;
        
        if (this.txtAttrName.getText().isEmpty()) {
            bReturn = false;
        }
        
        return bReturn;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtAttrName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxAttrType = new javax.swing.JComboBox();
        spinSize = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinPrecision = new javax.swing.JSpinner();
        cbxNullable = new javax.swing.JCheckBox();
        cbxPrimaryKey = new javax.swing.JCheckBox();
        cbxAutoIncrement = new javax.swing.JCheckBox();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Attribute Details"));

        jLabel1.setText("Attribute Name:");

        jLabel2.setText("Data Type:");

        jLabel3.setText("Size:");

        jLabel4.setText("Precision:");

        cbxNullable.setText("Nullable");

        cbxPrimaryKey.setText("Primary Key");

        cbxAutoIncrement.setText("Auto Increment");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAttrName)
                    .addComponent(cbxAttrType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinSize, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(cbxNullable)
                                    .addComponent(cbxPrimaryKey))
                                .addGap(12, 12, 12)))
                        .addComponent(spinPrecision, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbxAutoIncrement)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAttrName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxAttrType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(spinPrecision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxPrimaryKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxNullable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxAutoIncrement)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxAttrType;
    private javax.swing.JCheckBox cbxAutoIncrement;
    private javax.swing.JCheckBox cbxNullable;
    private javax.swing.JCheckBox cbxPrimaryKey;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner spinPrecision;
    private javax.swing.JSpinner spinSize;
    private javax.swing.JTextField txtAttrName;
    // End of variables declaration//GEN-END:variables
}