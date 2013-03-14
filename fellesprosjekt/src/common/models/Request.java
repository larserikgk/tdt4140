package common.models;

public class Request {
	private String query;
	private int type;
	public static int QUERY = 1, LOGOUT = 2;
	
	public Request(String query, int type) {
		this.query = query;
		this.type =  type;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}
