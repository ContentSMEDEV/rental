package com.saber.view;

import com.saber.event.DashboardEvent.UserLoginRequestedEvent;
import com.saber.event.DashboardEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.saber.main.services.UserService;
import javax.swing.JOptionPane;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();

        
        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        Notification notification = new Notification(
                "Welcome to Thai Rent Eco Car Dashboard");
        notification
                .setDescription("<span>Thai Rent Eco Car - All rights reserved &cpy; 2017 .</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(3000);
        notification.show(Page.getCurrent());
    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;
    }

    private Component buildFields() {

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Password");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
        signin.focus();

        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {

                if(username.getValue()== "" || password.getValue() == "") {
                    new Notification("ไม่สามารถเข้าสู่ระบบ",
                            "<i>กรุณาตรวจสอบบัญชีผู้ใช้งานอีกครั้ง</i>",
                            Notification.Type.WARNING_MESSAGE, true)
                            .show(Page.getCurrent());


                    Notification notification = new Notification(
                            "ไม่สามารถเข้าสู่ระบบ");
                    notification
                            .setDescription("<i>กรุณาตรวจสอบบัญชีผู้ใช้งานอีกครั้ง</i>");
                    notification.setHtmlContentAllowed(true);
                    notification.setStyleName("tray warning small closable login-help");
                    notification.setPosition(Position.BOTTOM_CENTER);
                    notification.setDelayMsec(3000);
                    notification.show(Page.getCurrent());


                }
                else{
                    DashboardEventBus.post(new UserLoginRequestedEvent(username.getValue(), password.getValue()));

                }



//                DashboardEventBus.post(new UserLoginRequestedEvent(username
//                        .getValue(), password.getValue()));
            }
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("ThaiRent - EcoCar");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

}
