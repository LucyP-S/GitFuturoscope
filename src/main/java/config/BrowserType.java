package config;

public enum BrowserType {
    CHROME("Chrome"),
    FIREFOX("Firefox"),
    EDGE("Edge)");

    private final String selectedBrowser;

    BrowserType(String selectedBrowser) {
        this.selectedBrowser = selectedBrowser;
    }
}
