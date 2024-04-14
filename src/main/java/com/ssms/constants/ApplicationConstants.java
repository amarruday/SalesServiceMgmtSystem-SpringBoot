package com.ssms.constants;

public interface ApplicationConstants {
	public interface RoleConstants {
		public final static String ROLE_ADMIN = "ADMIN";
		public final static String ROLE_SALES_MANAGER = "SALES_MANAGER";
		public final static String ROLE_SALES_ENGINEER = "SALES_ENGINEER";
		public final static String ROLE_SERVICE_MANAGER = "SERVICE_MANAGER";
		public final static String ROLE_SERVICE_ENGINEER = "SERVICE_ENGINEER";
	}

	public interface ResponseConstants {
		public final static String RESPONSE_FAILURE = "FAILURE";
		public final static String RESPONSE_SUCCESS = "SUCCESS";
		public final static String GENERIC_ERROR_MSG = "Generic Exception..! Please contact to your admin.";
	}

	public interface DepartmentConstants {
		public final static String SALES_AND_SERIVCE = "SALES_AND_SERIVCE";
		public final static String SALES_DEPARMENT = "SALES";
		public final static String SERVICE_DEPARTMENT = "SERVICE";
	}

	public interface CustomerConstants {
		public final static String CUSTOMER_TYPE_PROSPECT = "Prospect";
		public final static String CUSTOMER_TYPE_CUSTOMER = "Customer";
		public final static String CUSTOMER_STATUS_ACTIVE = "Active";
		public final static String CUSTOMER_STATUS_INACTIVE = "Inactive";
		public final static String ACTION_TYPE_ACTIVE = "Active";
		public final static String ACTION_TYPE_INACTIVE = "Inactive";
	}

	public interface EnquiryConstants {
		public final static String ENQUIRY_STATUS_ASSIGNED = "Assigned";
		public final static String ENQUIRY_STATUS_CANCELLED = "Cancelled";
		public final static String ENQUIRY_STATUS_LOST = "Lost";
		public final static String ENQUIRY_STATUS_PROSPECT = "Prospect";
		public final static String ENQUIRY_STATUS_UNASSIGNED = "Unassigned";
		public final static String ENQUIRY_STATUS_WON = "Won";
	}

	public interface TicketConstants {
		public final static String TICKET_TYPE_STATUS_ACTIVE = "Active";
		public final static String TICKET_TYPE_STATUS_INACTIVE = "Inactive";
		public final static String TICKET_STATUS_UNASSIGNED = "Unassigned";
		public final static String TICKET_STATUS_ASSIGNED = "Assigned";
		public final static String TICKET_STATUS_INPROGRESS= "InProgress";
		public final static String TICKET_STATUS_CANCELLED= "Cancelled";
		public final static String TICKET_STATUS_COMPLETED= "Completed";
		public final static String TICKET_STATUS_CLOSED= "Closed";
		
		//Priority
		public final static String TICKET_PRIORITY_LOW = "Low";
		public final static String TICKET_PRIORITY_MEDIUM = "Medium";
		public final static String TICKET_PRIORITY_HIGH = "High";
	}
	
	public interface OtpConstants {
		public final static String OTP_PURPOSE_FORGOT_PASSWORD = "Forgot Password";
		public final static String OTP_PURPOSE_CHANGE_PASSWORD = "Change Password";
		public static final long OTP_VALID_DURATION = 10 * 60 * 1000;   // 10 minutes
	}
}
