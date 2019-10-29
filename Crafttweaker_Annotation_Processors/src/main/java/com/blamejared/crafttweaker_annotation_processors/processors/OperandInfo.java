package com.blamejared.crafttweaker_annotation_processors.processors;

public class OperandInfo {
	private String operandName = null;
	private String operandType = null;
	private String operandDescription = null;
	private String operandExample = null;

	public String getOperandName() {
		return operandName != null ? operandName : "my" + operandType;
	}

	public void setOperandName(String operandName) {
		this.operandName = operandName;
	}

	public String getOperandType() {
		return operandType;
	}

	public void setOperandType(String operandType) {
		this.operandType = operandType;
	}

	public String getOperandDescription() {
		return hasOperandDescription() ? operandDescription : "No information given.";
	}

	public void setOperandDescription(String operandDescription) {
		this.operandDescription = operandDescription;
	}

	public String getOperandExample() {
		return hasOperandExample() ? operandExample : getOperandName();
	}

	public void setOperandExample(String operandExample) {
		this.operandExample = operandExample;
	}

	public boolean hasOperandDescription() {
		return operandDescription != null;
	}

	public boolean hasOperandExample() {
		return operandExample != null;
	}

}
