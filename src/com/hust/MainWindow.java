package com.hust;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainWindow extends javax.swing.JFrame {

    String f, DC, inputF;
    List<String> f2Bits = new ArrayList<>();
    List<String> inputF2Bits = new ArrayList<>();
    List<String> finalBits;
    List<String> finalF;
    Algorithm algorithm = new Algorithm();

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    private void StartActionPerformed(java.awt.event.ActionEvent evt) {
        switch (varType.getSelectedItem().toString()){
            case "Normal" ->{
                if(fInput.getText() != "") f = fInput.getText();
                DC = dcInput.getText();
                inputF = f + '+' + DC;
                processInfo.setText("Ham F: "+f+"\n");
                processInfo.setText(processInfo.getText()+"Dont care: "+DC+"\n");
                f2Bits = normalF2Bits(f);
                inputF2Bits = normalF2Bits(inputF);
                processInfo.setText(processInfo.getText()+"\nF in bit : \n");
                for(String f2Bit: f2Bits){
                    processInfo.setText(processInfo.getText()+"- " +f2Bit+"\n");
                }
                algorithm.optimizePreProcessBits(inputF2Bits,false);
                finalBits = algorithm.getFinalBits();
                processInfo.setText(processInfo.getText()+"Final bits: \n");
                for(String finalBit : finalBits){
                    processInfo.setText(processInfo.getText()+"- "+finalBit+"\n");
//                    System.out.println("- "+finalBit);
                }
                algorithm.minimizedF(finalBits,f2Bits);
                finalF = algorithm.getFinalF();
                finaFOutput.setText("Minimized F: \n");
                for(String s: finalF){
                    finaFOutput.setText(finaFOutput.getText()+"- F: "+s+"\n" );
                }
            }

            case "Minterm" ->{
                if(fInput.getText() != "") f = fInput.getText();
                DC = dcInput.getText();
                inputF = f + ',' + DC;
                processInfo.setText("Ham F: "+f+"\n");
                processInfo.setText(processInfo.getText()+"Dont care: "+DC+"\n");
                f2Bits = minterm2Bits(f, (Integer) varNum.getSelectedItem());
                inputF2Bits = minterm2Bits(inputF, (Integer) varNum.getSelectedItem());
                processInfo.setText(processInfo.getText()+"\nF in bit : \n");
                for(String f2Bit: f2Bits){
                    processInfo.setText(processInfo.getText()+"- " +f2Bit+"\n");
                }
                algorithm.optimizePreProcessBits(inputF2Bits,false);
                finalBits = algorithm.getFinalBits();
                processInfo.setText(processInfo.getText()+"Final bits: \n");
                for(String finalBit : finalBits){
                    processInfo.setText(processInfo.getText()+"- "+finalBit+"\n");
//                    System.out.println("- "+finalBit);
                }
                algorithm.minimizedF(finalBits,f2Bits);
                finalF = algorithm.getFinalF();
                finaFOutput.setText("Minimized F: \n");
                for(String s: finalF){
                    finaFOutput.setText(finaFOutput.getText()+"- F: "+s+"\n" );
                }
            }
        }
    }

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {
        fInput.setText("");
        dcInput.setText("");
        processInfo.setText("");
        finaFOutput.setText("");
    }

    static List<String> normalF2Bits(String f){
        List<String> result = new ArrayList<>();
        List<String> component = new ArrayList<>();

        for(int i = 0 ;i<f.length();i++){
            StringBuilder tmp = new StringBuilder();
            for(int j = i; j<f.length();j++){
                if(f.charAt(j) != '+') tmp.append(f.charAt(j));
                if(f.charAt(j) == '+' || j == f.length()-1) {
                    if(!component.contains(tmp.toString()))
                        component.add(tmp.toString());
                    i = j ;
                    break;
                }
            }
        }
        for (String s : component) {
            StringBuilder bit = new StringBuilder();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) != '`') bit.append('1');
                else bit.setCharAt(bit.length() - 1, '0');
            }
            result.add(bit.toString());
        }
        return result;
    }

    static List<String> minterm2Bits(String f, int varNums){
        List<String> result = new ArrayList<>();
        String[] component = f.split(",");

        for (String s : component) {
            int tmp = Integer.parseInt(s);
            result.add(intToBinary(tmp, varNums));
        }
        return result;
    }

    static String intToBinary(int n, int bit)
    {
        StringBuilder result = new StringBuilder();
        String s = Integer.toBinaryString(n);
        if(s.length() < bit){
            result.append("0".repeat(bit - s.length()));
        }
        result.append(s);
        return result.toString();
    }

//-------------------------------------------------------------------------------
    //UI COMPONENTS INIT

    private JComboBox<String> varType ;
    private javax.swing.JButton Clear;
    private javax.swing.JTextField dcInput;
    private javax.swing.JTextField fInput;
    private javax.swing.JButton Start;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea processInfo;
    private javax.swing.JTextArea finaFOutput;
    private JTextArea inputConstraint;
    private JComboBox<Integer> varNum;
    private JScrollPane jScrollPane3;

    public MainWindow(){
        createUIComponents();
    }
    @SuppressWarnings("unchecked")
    private void createUIComponents() {

        Start = new javax.swing.JButton();
        Clear = new javax.swing.JButton();
        fInput = new javax.swing.JTextField();
        dcInput = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        processInfo = new javax.swing.JTextArea();
        varType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        finaFOutput = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        varNum = new JComboBox<>();
        inputConstraint = new JTextArea();
        jScrollPane3 = new JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mini project - Digital Electronics"); // NOI18N
        setBackground(new java.awt.Color(204, 204, 204));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("mainFrame"); // NOI18N
        setResizable(false);
        setPreferredSize(new Dimension(800,600));

        Start.setText("Start");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        Clear.setText("Clear");
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });


        processInfo.setColumns(20);
        processInfo.setRows(5);
        processInfo.setLineWrap(true);
        processInfo.setEditable(false);
        processInfo.setFont(new Font("Consolas",0,18));
        jScrollPane1.setViewportView(processInfo);

        varType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Minterm" }));
        varType.setToolTipText("Choose f type");
        varNum.setModel(new DefaultComboBoxModel<>(new Integer[] {1,2,3,4,5,6,7,8,9,10}));
        varNum.setToolTipText("Choose number of variables");
        inputConstraint.setColumns(20);
        inputConstraint.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        inputConstraint.setLineWrap(true);
        inputConstraint.setRows(5);
        inputConstraint.setTabSize(5);
        inputConstraint.setText("- Type of input: \n   + Normal : abcd+a`bc`d+a`bcd+abc`d\n   + Minterm: 0,2,4,5,6,7,9\n- Input of F and Don't care only support full form of variables. For example: f(a,b,c,d) = a`b`c`d`+bc`d+acd+a`bcd, we need to manually transform it to a`b`c`d`+(abc`d+a`bc`d)+(abcd+ab`cd)+a`bcd.\n- Input doesn't support redundant space\n- ` means not ");
        inputConstraint.setWrapStyleWord(true);
        inputConstraint.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        inputConstraint.setEditable(false);
        jScrollPane3.setViewportView(inputConstraint);

        jLabel1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel1.setText("Ham F");

        jLabel2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel2.setText("Don't care");

        finaFOutput.setColumns(20);
        finaFOutput.setRows(5);
        finaFOutput.setEditable(false);
        finaFOutput.setLineWrap(true);
        finaFOutput.setFont(new Font("Consolas",0,18));
        jScrollPane2.setViewportView(finaFOutput);

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel3.setText("Nguyen Nhat Duy 20160772");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(Start)
                                                        .addComponent(Clear))
                                                .addGap(36, 36, 36)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(varNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(varType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(dcInput, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(fInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                                        .addComponent(jScrollPane3))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addComponent(jScrollPane2))
                                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(fInput)
                                                        .addComponent(jLabel1))
                                                .addGap(35, 35, 35)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(dcInput)
                                                        .addComponent(jLabel2))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(varType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Start))
                                                .addGap(23, 23, 23)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(Clear)
                                                        .addComponent(varNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3))
                                .addGap(32, 32, 32))
        );
        pack();
        setLocationRelativeTo(null);
    }
}
