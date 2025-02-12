import java.awt.Dimension;

import javax.swing.JButton;

public class Buttons {
    String btnName;
    Buttons(String btnName){
        this.btnName = btnName;
    }

    public JButton createBtn(){
        JButton createBtn = new JButton("Create " + this.btnName);
        createBtn.setPreferredSize(new Dimension(150,60));
        return createBtn;
    }
    public JButton searchBtn(){
        JButton searchBtn = new JButton("Search " + this.btnName);
        searchBtn.setPreferredSize(new Dimension(150,60));
        return searchBtn;
    }
    public JButton updateBtn(){
        JButton updateBtn = new JButton("Update " + this.btnName);
        updateBtn.setPreferredSize(new Dimension(150,60));
        return updateBtn;
    }
    public JButton deleteBtn(){
        JButton deleteBtn = new JButton("Delete " + this.btnName);
        deleteBtn.setPreferredSize(new Dimension(150,60));
        return deleteBtn;
    }
}
