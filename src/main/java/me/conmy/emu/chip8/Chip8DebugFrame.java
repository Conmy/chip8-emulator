package me.conmy.emu.chip8;

import me.conmy.emu.chip8.operations.OperationFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import java.awt.*;

public class Chip8DebugFrame extends JFrame {

    JTextArea debugApplicationTextArea;

    public void createAndShowGUI() {
        JScrollPane jsp = new JScrollPane();
        jsp.setSize(this.getPreferredSize());
        debugApplicationTextArea = new JTextArea();
        JTextArea lines = new JTextArea("1,2");
        lines.setBackground(Color.gray);
        debugApplicationTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
                int caretPosition = debugApplicationTextArea.getDocument().getLength();
                Element root = debugApplicationTextArea.getDocument().getDefaultRootElement();
                String text = "512" + System.getProperty("line.separator");
                int pc1=3;
                int pc2=4;
                for (int i=2; i < root.getElementIndex(caretPosition) + 2; i++) {
                    text += (512 + pc2 - 2) + System.getProperty("line.separator");
                    pc1+=2;
                    pc2+=2;
                }
                return text;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                lines.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lines.setText(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lines.setText(getText());
            }
        });
        jsp.getViewport().add(debugApplicationTextArea);
        jsp.setRowHeaderView(lines);
        add(jsp);

        setSize(400, 275);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setByteCode(byte[] applicationCode) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < applicationCode.length; i++) {
            sb.append(String.format("%02X", applicationCode[i]));
            if (i > 0 && ((i+1) % 2 == 0)) {
                int opCode1 = (Byte.toUnsignedInt(applicationCode[i-1]) << 8);
                int opCode2 = Byte.toUnsignedInt(applicationCode[i]);
                int opCode = opCode1 | opCode2;
                char opCodeChar = (char) opCode;
                sb.append(" ");
                try {
                    sb.append(OperationFactory.decodeOpCodeToOperation(opCodeChar).toString());
                } catch (RuntimeException e) {

                }
                sb.append(String.format("\n"));
            }
        }
        debugApplicationTextArea.setText(sb.toString());
    }
}
