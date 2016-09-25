package app.domain;

public enum Cargo {

	ESTAGIARIO("Estagiario"),
	TECNICO("Técnico"),
	ANALISTA("Analista");
	
	private String label;
	
	private Cargo(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
