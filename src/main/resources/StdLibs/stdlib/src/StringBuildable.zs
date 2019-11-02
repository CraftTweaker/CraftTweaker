public interface StringBuildable {
	toString(output as StringBuilder`borrow) as void;
	
	as string
		=> new StringBuilder() << this;
}
