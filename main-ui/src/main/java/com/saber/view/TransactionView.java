package com.saber.view;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import com.google.common.eventbus.Subscribe;
import com.saber.event.DashboardEvent.BrowserResizeEvent;
import com.saber.domain.User;
import com.saber.event.DashboardEventBus;
import com.saber.main.MyUI;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.saber.component.BookingAddUpdateWindow;

public final class TransactionView extends VerticalLayout implements View  {
	 	private final Table table;
	    private Button createBooking;
	    private static final DateFormat DATEFORMAT = new SimpleDateFormat(
	            "MM/dd/yyyy hh:mm:ss a");
	    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
	    private static final String[] DEFAULT_COLLAPSIBLE = { "country", "city",
	            "theater", "room", "title", "seats" };
	    
	    public TransactionView() {
	        setSizeFull();
	        addStyleName("transactions");
	        DashboardEventBus.register(this);

	        addComponent(buildToolbar());

	        table = buildTable();
	        addComponent(table);
	        setExpandRatio(table, 1);
	    }
	    
	    @Override
	    public void detach() {
	        super.detach();
	        // A new instance of TransactionsView is created every time it's
	        // navigated to so we'll need to clean up references to it on detach.
	        DashboardEventBus.unregister(this);
	    }
	    
	    private Component buildToolbar() {
	        HorizontalLayout header = new HorizontalLayout();
	        header.addStyleName("viewheader");
	        header.setSpacing(true);
	        Responsive.makeResponsive(header);

	        Label title = new Label("Transaction");
	        title.setSizeUndefined();
	        title.addStyleName(ValoTheme.LABEL_H1);
	        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	        header.addComponent(title);

	        createBooking = buildCreateBooking();
	        HorizontalLayout tools = new HorizontalLayout(buildFilter(),
	        		createBooking);
	        tools.setSpacing(true);
	        tools.addStyleName("toolbar");
	        header.addComponent(tools);

	        return header;
	    }
	    
	    private User getCurrentUser() {
	        return (User) VaadinSession.getCurrent().getAttribute(
	                User.class.getName());
	    }
	    
	    private Button buildCreateBooking() {
	    	final User user = getCurrentUser();
	        final Button createBooking = new Button("Add/Update Booking");
	        createBooking
	                .setDescription("Create a new report from the selected transactions");
	        createBooking.addClickListener(new ClickListener() {
	            @Override
	            public void buttonClick(final ClickEvent event) {
	            	BookingAddUpdateWindow.open(user, false);
	            }
	        });
	        createBooking.setEnabled(true);
	        return createBooking;
	    }
	    
	    private Component buildFilter() {
	        final TextField filter = new TextField();
	        filter.addTextChangeListener(new TextChangeListener() {
	            @Override
	            public void textChange(final TextChangeEvent event) {
	                //Filterable data = (Filterable) table.getContainerDataSource();
	                //data.removeAllContainerFilters();
	                /*data.addContainerFilter(new Filter() {
	                	@Override
	                    public boolean passesFilter(final Object itemId,
	                            final Item item) {

	                        if (event.getText() == null
	                                || event.getText().equals("")) {
	                            return true;
	                        }

	                        return filterByProperty("country", item,
	                                event.getText())
	                                || filterByProperty("city", item,
	                                        event.getText())
	                                || filterByProperty("title", item,
	                                        event.getText());

	                    }

	                    @Override
	                    public boolean appliesToProperty(final Object propertyId) {
	                        if (propertyId.equals("country")
	                                || propertyId.equals("city")
	                                || propertyId.equals("title")) {
	                            return true;
	                        }
	                        return false;
	                    }
	                });*/
	            }
	        });

	        filter.setInputPrompt("Filter");
	        filter.setIcon(FontAwesome.SEARCH);
	        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	        /*filter.addShortcutListener(new ShortcutListener("Clear",
	                KeyCode.ESCAPE, null) {
	            @Override
	            public void handleAction(final Object sender, final Object target) {
	                filter.setValue("");
	                ((Filterable) table.getContainerDataSource())
	                        .removeAllContainerFilters();
	            }
	        });*/
	        return filter;
	    }

	    private Table buildTable() {
	        final Table table = new Table() {
	            @Override
	            protected String formatPropertyValue(final Object rowId,
	                    final Object colId, final Property<?> property) {
	                String result = super.formatPropertyValue(rowId, colId,
	                        property);
	                if (colId.equals("time")) {
	                    result = DATEFORMAT.format(((Date) property.getValue()));
	                } else if (colId.equals("price")) {
	                    if (property != null && property.getValue() != null) {
	                        return "$" + DECIMALFORMAT.format(property.getValue());
	                    } else {
	                        return "";
	                    }
	                }
	                return result;
	            }
	        };
	        table.setSizeFull();
	        table.addStyleName(ValoTheme.TABLE_BORDERLESS);
	        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
	        table.addStyleName(ValoTheme.TABLE_COMPACT);
	        table.setSelectable(true);

	        table.setColumnCollapsingAllowed(true);
	        table.setColumnCollapsible("time", false);
	        table.setColumnCollapsible("price", false);

	        table.setColumnReorderingAllowed(true);
	        /*table.setContainerDataSource(new TempTransactionsContainer(DashboardUI
	                .getDataProvider().getRecentTransactions(200)));*/
	        //table.setSortContainerPropertyId("time");
	        table.setSortAscending(false);

	        //table.setColumnAlignment("seats", Align.RIGHT);
	        //table.setColumnAlignment("price", Align.RIGHT);

	        //table.setVisibleColumns("time", "price");
	        //table.setColumnHeaders("Time",  "Price");



	        // Allow dragging items to the reports menu
	       // table.setDragMode(TableDragMode.MULTIROW);
	        table.setMultiSelect(true);
	        /*
	        table.addActionHandler(new TransactionsActionHandler());

	        table.addValueChangeListener(new ValueChangeListener() {
	            @Override
	            public void valueChange(final ValueChangeEvent event) {
	                if (table.getValue() instanceof Set) {
	                    Set<Object> val = (Set<Object>) table.getValue();
	                    createReport.setEnabled(val.size() > 0);
	                }
	            }
	        });*/
	        table.setImmediate(true);

	        return table;
	    }
	    
	    private boolean defaultColumnsVisible() {
	        boolean result = true;
	        for (String propertyId : DEFAULT_COLLAPSIBLE) {
	            if (table.isColumnCollapsed(propertyId) == Page.getCurrent()
	                    .getBrowserWindowWidth() < 800) {
	                result = false;
	            }
	        }
	        return result;
	    }

	    @Subscribe
	    public void browserResized(final BrowserResizeEvent event) {
	        // Some columns are collapsed when browser window width gets small
	        // enough to make the table fit better.
	        if (defaultColumnsVisible()) {
	            for (String propertyId : DEFAULT_COLLAPSIBLE) {
	                table.setColumnCollapsed(propertyId, Page.getCurrent()
	                        .getBrowserWindowWidth() < 800);
	            }
	        }
	    }

		@Override
		public void enter(final ViewChangeEvent event) {
			// TODO Auto-generated method stub
			
		}


}
