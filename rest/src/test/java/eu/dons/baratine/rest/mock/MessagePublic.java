package eu.dons.baratine.rest.mock;

public class MessagePublic {

	String url;
	String value;

	public MessagePublic() {}
	public MessagePublic(String url) {
        this.url = url;
    }

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return String.format("{class=\"Message\", url=\"%s\", value=\"%s\"}", url, value);
	}

}
