package Controller;

import Model.Admin;
import View.AdminView;
import View.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private Admin model;
    private AdminView view;

    public AdminController(Admin model, AdminView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {

        view.getCloseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                
                LoginView LV = new LoginView();
                new LoginController(LV);
                LV.setVisible(true);
            }
        });
    }
}
