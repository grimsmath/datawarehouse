/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jetsonfuzz.java.gui;

import com.jetsonfuzz.java.dw.Database;
import com.jetsonfuzz.java.dw.SqlColumn;
import com.jetsonfuzz.java.dw.SqlTable;
import com.jetsonfuzz.java.dw.Warehouse;
import com.jetsonfuzz.java.main.Constants;
import com.jetsonfuzz.java.main.Properties;
import com.jetsonfuzz.java.main.Util;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dking
 */
public class FactTablePanel extends javax.swing.JPanel {
    private Properties _props = null;
    private Database _db = null;
    private Warehouse _dw = null;
    private String _factTableName = "";
    
    private SqlTable _factTable = null;
    private DefaultListModel<SqlTable> _tableModel = null;
    private DefaultListModel<SqlColumn> _columnModel = null;
    private DefaultListModel<SqlColumn> _keyModel = null;
    private DefaultListModel<SqlColumn> _nonKeyModel = null;
    
    /**
     * Creates new form FactTablePanel
     * @param props
     * @param db
     */
    public FactTablePanel(Properties props, Database db, Warehouse dw) {
        this._props = props;
        this._db = db;
        this._dw = dw;
        this._tableModel = new DefaultListModel<>();
        this._columnModel = new DefaultListModel<>();
        this._keyModel = new DefaultListModel<>();
        this._nonKeyModel = new DefaultListModel<>();
        
        initComponents();
        
        this.txtFactName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
            }
        });
                
        this.setupTableList();
        this.setupColumnList();
        this.setupKeyList();
        this.setupNonKeyList();   
        
        this.lblTableName.setText("Table Name: (NOTE: Fact tables are automatically pref ixed with \"" + 
                Constants.FactTablePrefix + "\" to avoid collisions with other tables)");
    }
    
    private void setupTableList() {
        this.listTable.setModel(this._tableModel);
        
        // Fill the table model
        for (SqlTable table : this._dw.getNewTables()) {
            this._tableModel.addElement(table);
        }

        this.listTable.updateUI();

        this.listTable.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (! ev.getValueIsAdjusting()) {
                    JList list = (JList) ev.getSource();
                    
                    populateColumnList(list.getSelectedValue().toString());
                }
            } 
        });
    }
    
    private void setupColumnList() {
        this.listColumn.setModel(this._columnModel);
    }
    
    private void setupKeyList() {
        this.listKeys.setModel(this._keyModel);
        this.listKeys.updateUI();
        
        this.listKeys.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (! ev.getValueIsAdjusting()) {
                    JList list = (JList) ev.getSource();
                    
                    SqlColumn col = (SqlColumn) list.getSelectedValue();
                    if (col != null) {
                        displayColumnDetails(col);
                    }
                }
            } 
        });
        
        this.listKeys.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                // Not implemented
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (! e.isTemporary()) {
                    if (e.getOppositeComponent() instanceof JList) {
                        JList list = (JList) e.getOppositeComponent();
                        list.clearSelection();
                    }
                }
            }
        });
    }
    
    private void setupNonKeyList() {
        this.listNonKeys.setModel(this._nonKeyModel);
        this.listNonKeys.updateUI();
        
        this.listNonKeys.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (! ev.getValueIsAdjusting()) {
                    JList list = (JList) ev.getSource();
                    
                    SqlColumn col = (SqlColumn) list.getSelectedValue();
                    if (col != null) {
                        displayColumnDetails(col);
                    }
                }
            } 
        });
        
        this.listNonKeys.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                // Not implemented
            }

            @Override
            public void focusGained(FocusEvent e) {
                // not implemented
                if (! e.isTemporary()) {
                    if (e.getOppositeComponent() instanceof JList) {
                        JList list = (JList) e.getOppositeComponent();
                        list.clearSelection();
                    }
                }
            }
        });
    }
    
    private void populateColumnList(String table) {
        // Remove previous contents
        this._columnModel.clear();
        
        SqlTable primaryTable = null;
        
        // Find the SqlTable using the supplied table string
        for (SqlTable tbl : this._dw.getNewTables()) {
            // Compare -- probably not the best search algorithm
            if (tbl.getNewName().compareTo(table) == 0) {
                // Found the SqlTable
                primaryTable = tbl;
                
                // Exit the loop
                break;
            }
        }
        
        // Get all the columns strings from the SqlTable and populate
        // the model
        for (SqlColumn col : primaryTable.getColumns()) {
            this._columnModel.addElement(col);
        }
        
        // Update the JList
        this.listColumn.updateUI();
    }
    
    private void displayColumnDetails(SqlColumn col) {
        this.lblAttrOrigName.setText(col.getOriginalName());
        this.txtAttributeName.setText(col.getNewName());
        this.cbxNullable.setSelected(col.getAllowNull());
        this.cbxPrimaryKey.setSelected(col.getPrimaryKey());
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
        lblTableName = new javax.swing.JLabel();
        txtFactName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtAttributeName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblAttrOrigName = new javax.swing.JLabel();
        cbxPrimaryKey = new javax.swing.JCheckBox();
        cbxNullable = new javax.swing.JCheckBox();
        cbxAutoIncrement = new javax.swing.JCheckBox();
        btnRevertChanges = new javax.swing.JButton();
        btnSaveChanges = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listTable = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listColumn = new javax.swing.JList();
        btnAssignNonKey = new javax.swing.JButton();
        btnAssignKey = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listKeys = new javax.swing.JList();
        btnRemoveKey = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listNonKeys = new javax.swing.JList();
        btnRemoveAttr = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnViewSQL = new javax.swing.JButton();
        btnSaveTable = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 1: Define Fact Table"));

        lblTableName.setText("Fact Table Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTableName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtFactName))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFactName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 6: Modify Attributes"));

        jLabel5.setText("New Attribute Name:");

        jLabel7.setText("Original Attribute Name:");

        lblAttrOrigName.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        lblAttrOrigName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAttrOrigName.setText("Unknown");
        lblAttrOrigName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbxPrimaryKey.setText("Primary Key");

        cbxNullable.setText("Nullable");

        cbxAutoIncrement.setText("Auto Increment");

        btnRevertChanges.setText("Revert Changes");

        btnSaveChanges.setText("Save Changes");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAttributeName)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                            .addComponent(lblAttrOrigName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(cbxNullable))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbxPrimaryKey)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxAutoIncrement)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnRevertChanges)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveChanges, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAttrOrigName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(txtAttributeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPrimaryKey)
                    .addComponent(cbxAutoIncrement))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxNullable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRevertChanges)
                    .addComponent(btnSaveChanges))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 2: Select Table"));

        jScrollPane1.setViewportView(listTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 4: Select Column"));

        jScrollPane2.setViewportView(listColumn);

        btnAssignNonKey.setText("Assign as Non-Key");
        btnAssignNonKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignNonKeyActionPerformed(evt);
            }
        });

        btnAssignKey.setText("Assign as Key");
        btnAssignKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAssignNonKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAssignKey, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAssignKey)
                .addGap(3, 3, 3)
                .addComponent(btnAssignNonKey))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(72, Short.MAX_VALUE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 4: Assign Keys"));

        jScrollPane3.setViewportView(listKeys);

        btnRemoveKey.setText("Remove Key");
        btnRemoveKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRemoveKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveKey))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 5: Non-Key Attributes"));

        jScrollPane4.setViewportView(listNonKeys);

        btnRemoveAttr.setText("Remove Non-Key");
        btnRemoveAttr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAttrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btnRemoveAttr, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveAttr))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 7: View & Save"));

        btnViewSQL.setText("View SQL");
        btnViewSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSQLActionPerformed(evt);
            }
        });

        btnSaveTable.setText("Save Table");
        btnSaveTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTable, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSaveTable, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(btnViewSQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignKeyActionPerformed
        int [] selectedItems = this.listColumn.getSelectedIndices();
        
        for (int i : selectedItems) {
            SqlColumn col = this._columnModel.getElementAt(i);
            this._keyModel.addElement(col);
        }
        
        this.listKeys.updateUI();
    }//GEN-LAST:event_btnAssignKeyActionPerformed

    private void btnRemoveKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveKeyActionPerformed
        int [] selectedItems = this.listKeys.getSelectedIndices();
        
        for (int i : selectedItems) {
            this._keyModel.removeElementAt(i);
        }
        
        this.listKeys.updateUI();
    }//GEN-LAST:event_btnRemoveKeyActionPerformed

    private void btnAssignNonKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignNonKeyActionPerformed
        int [] selectedItems = this.listColumn.getSelectedIndices();
        
        for (int i : selectedItems) {
            SqlColumn col = this._columnModel.getElementAt(i);
            this._nonKeyModel.addElement(col);
        }
        
        this.listNonKeys.updateUI();    }//GEN-LAST:event_btnAssignNonKeyActionPerformed

    private void btnViewSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSQLActionPerformed
        // Show the view panel
        ViewSqlPanel viewSQL = new ViewSqlPanel(saveTableDetails());
        JOptionPane pane = new JOptionPane(viewSQL,  
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);

        JDialog dialog = pane.createDialog(null, "Generated SQL DDL/DML");
        dialog.setVisible(true);

        if(pane.getValue() == null) {
            // User canceled
        } else {
            // User clicked OK
        }
    }//GEN-LAST:event_btnViewSQLActionPerformed

    private void btnSaveTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTableActionPerformed
        if (validateForm()) {
            this._dw.getFactTables().add(saveTableDetails());
            Util.showInfoBox("Save Fact Table", 
                    "The fact table has been successfully saved.");
        }
    }//GEN-LAST:event_btnSaveTableActionPerformed

    private void btnRemoveAttrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAttrActionPerformed
        int [] selectedItems = this.listNonKeys.getSelectedIndices();
        
        for (int i : selectedItems) {
            this._nonKeyModel.removeElementAt(i);
        }
        
        this.listNonKeys.updateUI();
    }//GEN-LAST:event_btnRemoveAttrActionPerformed

    public SqlTable saveTableDetails() {
        SqlTable newTable = new SqlTable();
        ArrayList<SqlColumn> cols = new ArrayList<>();
        
        newTable.setOriginalName(this.txtFactName.getText());
        newTable.setNewName(Constants.FactTablePrefix + this.txtFactName.getText());
        newTable.setCustomTable(true);
        
        // Key attributes
        for (Object obj : this._keyModel.toArray()) {
            SqlColumn col = (SqlColumn) obj;
            cols.add(col);
        }

        // Non-key attributes
        for (Object obj : this._nonKeyModel.toArray()) {
            SqlColumn col = (SqlColumn) obj;
            cols.add(col);
        }

        newTable.setColumns(cols);

        return newTable;
    }
    
    private boolean validateForm() {
        boolean bReturn = true;
        
        if (this.txtFactName.getText().isEmpty()) {
            Util.showErrorBox("Missing Table Name", 
                    "Please enter a table name before proceeding.");
            this.txtFactName.requestFocus();
            bReturn = false;
        }
        
        return bReturn;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssignKey;
    private javax.swing.JButton btnAssignNonKey;
    private javax.swing.JButton btnRemoveAttr;
    private javax.swing.JButton btnRemoveKey;
    private javax.swing.JButton btnRevertChanges;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnSaveTable;
    private javax.swing.JButton btnViewSQL;
    private javax.swing.JCheckBox cbxAutoIncrement;
    private javax.swing.JCheckBox cbxNullable;
    private javax.swing.JCheckBox cbxPrimaryKey;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAttrOrigName;
    private javax.swing.JLabel lblTableName;
    private javax.swing.JList listColumn;
    private javax.swing.JList listKeys;
    private javax.swing.JList listNonKeys;
    private javax.swing.JList listTable;
    private javax.swing.JTextField txtAttributeName;
    private javax.swing.JTextField txtFactName;
    // End of variables declaration//GEN-END:variables
}
