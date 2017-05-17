package com.saber.event;
import com.saber.domain.Transaction;
import com.saber.view.DashboardViewType;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.saber.main.services.UserService;
import com.saber.main.MyUI;
import java.util.Collection;

public class DashboardEvent {
	
	
	public static final class UserLoginRequestedEvent {
		private String userName="";
		private String password="";
		private UserService userService;

		public UserLoginRequestedEvent( String userName, String password) {
            this.userName = userName;
            this.password = password;
            
            
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
        
        public long loginVerify() {
        	
            return this.userService.loginVerify(this.userName, this.password);
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }

    public static class NotificationsCountUpdatedEvent {
    }

    public static final class ReportsCountUpdatedEvent {
        private final int count;

        public ReportsCountUpdatedEvent(final int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

    }

    public static final class TransactionReportEvent {
        private final Collection<Transaction> transactions;

        public TransactionReportEvent(final Collection<Transaction> transactions) {
            this.transactions = transactions;
        }

        public Collection<Transaction> getTransactions() {
            return transactions;
        }
    }

    public static final class PostViewChangeEvent {
        private final DashboardViewType view;

        public PostViewChangeEvent(final DashboardViewType view) {
            this.view = view;
        }

        public DashboardViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    }
}
