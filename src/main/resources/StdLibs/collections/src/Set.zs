public interface Set<T> {
	add(value as T) as bool;
	remove(value as T) as bool;
	
	get size as usize;
	
	toArray();
	toArray(comparator as function(a as T, b as T) as int);
	
	in(value as T) as bool;
	for(x as T);
}
