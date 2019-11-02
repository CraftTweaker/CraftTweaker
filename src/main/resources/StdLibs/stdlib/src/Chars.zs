public expand char {
	public times(number as usize) as string
		=> new string(new char[](number, this));
}
