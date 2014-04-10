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
public class DimTablePanel extends javax.swing.JPanel {
    private Properties _props = null;
    private Database _db = null;
    private Warehouse _dw = null;
    private DefaultListModel<SqlTable> _modelTables = null;
    private DefaultListModel<SqlColumn> _modelColumns = null;
    private DefaultListModel<SqlColumn> _modelKeys = null;
    private DefaultListModel<SqlColumn> _modelNonKeys = null;
    
    /**
     * Creates new form DimTablePanel
     * @param props
     * @param db
     * @param dw
     */
    public DimTablePanel(Properties props, Database db, Warehouse dw) {
        this._props = props;
        this._db = db;
        this._dw = dw;
        this._modelTables = new DefaultListModel<>();
        this._modelColumns = new DefaultListModel<>();
        this._modelKeys = new DefaultListModel<>();
        this._modelNonKeys = new DefaultListModel<>();
        
        initComponents();
        
        this.txtTableName.addKeyListener(new KeyAdapter() {
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
        
        this.lblTableName.setText("Table Name: (NOTE: Dimension tables are automatically prefixed with \"" + 
                Constants.NewTablePrefix + "\" to avoid collisions with transaction tables)");
    }
    
    private void setupTableList() {
        this.listSourceTables.setModel(this._modelTables);
        
        ArrayList<SqlTable> tables = this._db.getSqlTables();
                
        for (SqlTable table : tables) {
            this._modelTables.addElement(table);
        }
        
        this.listSourceTables.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (! ev.getValueIsAdjusting()) {
                    JList list = (JList) ev.getSource();
                    
                    populateColumnList(list.getSelectedValue().toString());
                }
            } 
        });
        
        this.listSourceTables.updateUI();
    }

    private void setupColumnList() {
        this.listColumns.setModel(this._modelColumns);
        this.listColumns.updateUI();
    }
    
    private void setupKeyList() {
        this.listKeyAttributes.setModel(this._modelKeys);
        this.listKeyAttributes.updateUI();
        
        this.listKeyAttributes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (! ev.getValueIsAdjusting()) {
                    JList list = (JList) ev.getSource();
                    if (! list.isSelectionEmpty()) {
                        displayColumnDetails((SqlColumn) list.getSelectedValue());
                    }
                }
            } 
        });
        
        this.listKeyAttributes.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                // Not implemented
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (! e.isTemporary()) {
                    if (e.getOppositeComponent() instanceof JList) {
                        JList list = (JList) e.getSource();
                        list.clearSelection();
                    }
                }
            }
        });
    }
    
    private void setupNonKeyList() {
        this.listNonKeyAttributes.setModel(this._modelNonKeys);
        this.listNonKeyAttributes.updateUI();
        
        this.listNonKeyAttributes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (! ev.getValueIsAdjusting()) {
                    JList list = (JList) ev.getSource();
                    if (! list.isSelectionEmpty()) {
                        displayColumnDetails((SqlColumn) list.getSelectedValue());
                    }
                }
            } 
        });
        
        this.listNonKeyAttributes.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                // Not implemented
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (! e.isTemporary()) {
                    if (e.getOppositeComponent() instanceof JList) {
                        JList list = (JList) e.getSource();
                        list.clearSelection();
                    }
                }
            }
        });
    }
    
    private void populateColumnList(String table) {
        // Remove previous contents
        this._modelColumns.clear();
        
        SqlTable primaryTable = null;
        
        // Find the SqlTable using the supplied table string
        for (SqlTable tbl : this._dw.getOriginalTables()) {
            // Compare -- probably not the best search algorithm
            if (tbl.getNewName().compareTo(table) == 0) {
                // Found the SqlTable
                primaryTable = tbl;
                
                // Exit the loop
                break;
            }
        }
        
        // Get all the columns strings from the SqlTable and populate the model
        for (SqlColumn col : primaryTable.getColumns()) {
            this._modelColumns.addElement(col);
        }
        
        // Update the JList
        this.listColumns.updateUI();
    }
    
    private void displayColumnDetails(SqlColumn col) {
        this.lblOriginalName.setText(col.getOriginalName());
        this.txtNewAttrName.setText(col.getNewName());
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
        jScrollPane2 = new javax.swing.JScrollPane();
        listSourceTables = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        txtTableName = new javax.swing.JTextField();
        lblTableName = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listColumns = new javax.swing.JList();
        btnAddKey = new javax.swing.JButton();
        btnAddNonKey = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listKeyAttributes = new javax.swing.JList();
        btnRemoveKey = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listNonKeyAttributes = new javax.swing.JList();
        btnRemoveNonKey = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblOriginalName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNewAttrName = new javax.swing.JTextField();
        cbxNullable = new javax.swing.JCheckBox();
        cbxPrimaryKey = new javax.swing.JCheckBox();
        btnRevertChanges = new javax.swing.JButton();
        btnSaveChanges = new javax.swing.JButton();
        chkAutoIncrement = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        btnViewSQL = new javax.swing.JButton();
        btnSaveCustomTable = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Source Tables"));

        jScrollPane2.setViewportView(listSourceTables);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dimension Table Details"));

        lblTableName.setText("Table Name:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTableName)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTableName)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableName)
                .addGap(4, 4, 4)
                .addComponent(txtTableName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Available Columns"));

        jScrollPane1.setViewportView(listColumns);

        btnAddKey.setText("Add as Key");
        btnAddKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKeyActionPerformed(evt);
            }
        });

        btnAddNonKey.setText("Add as Non-Key");
        btnAddNonKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNonKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btnAddKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAddNonKey, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddKey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddNonKey)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Key Attributes"));

        jScrollPane3.setViewportView(listKeyAttributes);

        btnRemoveKey.setText("Remove Attribute");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btnRemoveKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(btnRemoveKey)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Non-Key Attributes"));

        jScrollPane4.setViewportView(listNonKeyAttributes);

        btnRemoveNonKey.setText("Remove Attribute");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btnRemoveNonKey, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(btnRemoveNonKey)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Attribute Details"));

        jLabel2.setText("Original Attribute Name:");

        lblOriginalName.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        lblOriginalName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOriginalName.setText("Unknown");
        lblOriginalName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("New Attribute Name:");

        cbxNullable.setText("Nullable");

        cbxPrimaryKey.setText("Primary Key");

        btnRevertChanges.setText("Revert Changes");

        btnSaveChanges.setText("Save Changes");

        chkAutoIncrement.setText("Auto Increment");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnRevertChanges)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveChanges, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblOriginalName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNewAttrName)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxNullable)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(cbxPrimaryKey)
                                .addGap(18, 18, 18)
                                .addComponent(chkAutoIncrement)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOriginalName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNewAttrName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPrimaryKey)
                    .addComponent(chkAutoIncrement))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxNullable)
                .addGap(101, 101, 101)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRevertChanges)
                    .addComponent(btnSaveChanges))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        btnViewSQL.setText("View SQL");
        btnViewSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSQLActionPerformed(evt);
            }
        });

        btnSaveCustomTable.setText("Save Custom Table");
        btnSaveCustomTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveCustomTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewSQL, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveCustomTable)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnViewSQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSaveCustomTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKeyActionPerformed
        int [] selectedItems = this.listColumns.getSelectedIndices();
        
        for (int i : selectedItems) {
            SqlColumn col = this._modelColumns.getElementAt(i);
            this._modelKeys.addElement(col);
        }
        
        this.listKeyAttributes.updateUI();
    }//GEN-LAST:event_btnAddKeyActionPerformed

    private void btnAddNonKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNonKeyActionPerformed
        int [] selectedItems = this.listColumns.getSelectedIndices();
        
        for (int i : selectedItems) {
            SqlColumn col = this._modelColumns.getElementAt(i);
            this._modelNonKeys.addElement(col);
        }
        
        this.listNonKeyAttributes.updateUI();
    }//GEN-LAST:event_btnAddNonKeyActionPerformed

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

    private void btnSaveCustomTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveCustomTableActionPerformed
        if (validateForm()) {
            this._dw.getNewTables().add(saveTableDetails());
            Util.showInfoBox("Save Table", 
                    "The custom table has been successfully saved.");
        }
    }//GEN-LAST:event_btnSaveCustomTableActionPerformed

    private SqlTable saveTableDetails() {
        SqlTable newTable = new SqlTable();

        newTable.setOriginalName(this.txtTableName.getText());
        newTable.setNewName(Constants.NewTablePrefix + this.txtTableName.getText());
        newTable.setCustomTable(true);
        
        // Key attributes
        ArrayList<SqlColumn> cols = new ArrayList<>();
        for (Object obj : this._modelKeys.toArray()) {
            SqlColumn col = (SqlColumn) obj;
            cols.add(col);
        }

        // Non-key attributes
        for (Object obj : this._modelNonKeys.toArray()) {
            SqlColumn col = (SqlColumn) obj;
            cols.add(col);
        }

        newTable.setColumns(cols);

        return newTable;
    }
    
    private boolean validateForm() {
        boolean bReturn = true;
        
        if (this.txtTableName.getText().isEmpty()) {
            Util.showErrorBox("Table Name Empty", "Please enter a table name.");
            this.txtTableName.requestFocus();
            bReturn = false;
        }
        
        return bReturn;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddKey;
    private javax.swing.JButton btnAddNonKey;
    private javax.swing.JButton btnRemoveKey;
    private javax.swing.JButton btnRemoveNonKey;
    private javax.swing.JButton btnRevertChanges;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnSaveCustomTable;
    private javax.swing.JButton btnViewSQL;
    private javax.swing.JCheckBox cbxNullable;
    private javax.swing.JCheckBox cbxPrimaryKey;
    private javax.swing.JCheckBox chkAutoIncrement;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel lblOriginalName;
    private javax.swing.JLabel lblTableName;
    private javax.swing.JList listColumns;
    private javax.swing.JList listKeyAttributes;
    private javax.swing.JList listNonKeyAttributes;
    private javax.swing.JList listSourceTables;
    private javax.swing.JTextField txtNewAttrName;
    private javax.swing.JTextField txtTableName;
    // End of variables declaration//GEN-END:variables
}
