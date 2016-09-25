package app.domain;

public enum CorCabelo {

	PRETO("Preto"),
	CASTANHO("Castanho"),
	LOIRO("Loiro"),
	RUIVO("Ruivo");
	
	private String label;
	
	private CorCabelo(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
