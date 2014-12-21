package Dziecioly.zkimnabasen.baza.model;

public class RowBeanListaWyd {
    private int icon;
    private String tekst;
    
    public RowBeanListaWyd () { }
    
    public RowBeanListaWyd(int icon, String tekst) {
    	this.icon = icon;
    	this.tekst = tekst;
    }

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
}

