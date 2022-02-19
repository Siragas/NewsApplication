package repository;




public class Search {
	
	private int id;
	private String method;
	private String country;
	private String category;
	private String keyword;
	private String sources;
	private String from;
	private String to;
	private String language;
	private int accountid;
	
	public Search(int id, String method, String country, String category, String keyword, String sources, String from,
			String to, String language, int accountid) {
		super();
		this.id = id;
		this.method = method;
		this.country = country;
		this.category = category;
		this.keyword = keyword;
		this.sources = sources;
		this.from = from;
		this.to = to;
		this.language = language;
		this.accountid = accountid;
	}
	
	

	public Search() {
		super();
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	@Override
	public String toString() {
		return "Search [id=" + id + ", method=" + method + ", country=" + country + ", category=" + category
				+ ", keyword=" + keyword + ", sources=" + sources + ", from=" + from + ", to=" + to + ", language="
				+ language + "]";
	}
	
	
	
}