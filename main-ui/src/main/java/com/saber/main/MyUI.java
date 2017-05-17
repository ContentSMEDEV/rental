package com.saber.main;

import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.Subscribe;
import com.saber.data.DataProvider;
import com.saber.data.dummy.DummyDataProvider;
import com.saber.domain.User;
import com.saber.event.DashboardEvent.CloseOpenWindowsEvent;
import com.saber.event.DashboardEvent.UserLoggedOutEvent;
import com.saber.event.DashboardEvent.UserLoginRequestedEvent;
import com.saber.event.DashboardEventBus;
import com.saber.main.backend.CrudService;
import com.saber.view.LoginView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.MenuBar;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.saber.event.DashboardEvent.BrowserResizeEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@Theme("assets")
public class MyUI extends UI {

    private CrudService<Person> service = new CrudService<>();
    private BeanItemContainer<Person> dataSource = new BeanItemContainer<Person>(Person.class);
    
    
    private final DataProvider dataProvider = new DummyDataProvider();
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	setLocale(Locale.US);

        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();
        Page.getCurrent().addBrowserWindowResizeListener(
                new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        DashboardEventBus.post(new BrowserResizeEvent());
                    }
                });
        
        final VerticalLayout layout = new VerticalLayout();
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            service.save(new Person(name.getValue()));
            dataSource.removeAllItems();
            dataSource.addAll(service.findAll());
        });

        Grid grid = new Grid(dataSource);
        grid.setSizeFull();

        //V
        // Set the root layout for the UI
        VerticalLayout content = new VerticalLayout();
        
        //header
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        
        //menu bar
        MenuBar menuBar = new MenuBar();
        header.addComponent(menuBar);
        MenuBar.MenuItem settingsItem = menuBar.addItem("", FontAwesome.WRENCH, null);
        MenuBar.MenuItem useLargeIconsItem = settingsItem.addItem("Use large icons", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
              
            }
        });

        
        content.addComponent(header);
       
        // This is a component from the main-addon module
        //layout.addComponent(new MyComponent());
        //layout.addComponents(content);
       // layout.addComponents(name, button, grid);
        //layout.setSizeFull();
        //layout.setMargin(true);
        //layout.setSpacing(true);
        //layout.setExpandRatio(grid, 1.0f);

        //setContent(layout);
    }
    
    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        if (user != null && "admin".equals(user.getRole())) {
            // Authenticated user
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }
    
    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        User user = getDataProvider().authenticate(event.getUserName(),
                event.getPassword());
        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
        updateContent();
    }
    
    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
    
    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((MyUI) getCurrent()).dataProvider;
    }
    
    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }
    
    public static DashboardEventBus getDashboardEventbus() {
        return ((MyUI) getCurrent()).dashboardEventbus;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
