package main.view;

import javax.swing.*;

public class View extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Used to switch between views
     *
     * @param show view that needs to be shown
     * @param hide view that needs to be hidden
     */
    public void changeTo(View show, View hide) {
        System.out.println("VIEW: changeTo called");
        show.setVisible(true);
        hide.close();

    }

    /**
     * Close view
     */
    public void close() {
        this.dispose();
    }
}