package com.saber.view;
import com.saber.view.DashboardView;
import com.saber.view.reports.ReportsView;
import com.saber.view.sales.SalesView;
import com.saber.view.schedule.ScheduleView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true),
	CUSTOMERS("customers", CustomerView.class, FontAwesome.USERS, false),
    BOOKING("booking", BookingView.class, FontAwesome.TABLE, false),
	MANAGE("manage", ManageView.class, FontAwesome.TABLE, false),
	TRANSACTION("transaction", TransactionView.class, FontAwesome.DOLLAR, false);
    /*, SALES(
            "sales", SalesView.class, FontAwesome.BAR_CHART_O, false), TRANSACTIONS(
            "transactions", TransactionsView.class, FontAwesome.TABLE, false), REPORTS(
            "reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true), SCHEDULE(
            "schedule", ScheduleView.class, FontAwesome.CALENDAR_O, false);*/

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
