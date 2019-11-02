[Native("stdlib::Arrays")]
public expand <T : Comparable<T>> T[] {
	[Native("sort")]
	public extern sort() as void;
	[Native("sorted")]
	public extern sorted() as T[];
}

public expand <T : Hashable<T>> T[] {
	public implements Hashable<T[]> {
		public extern hashCode() as int;
		public extern == (other as T[]) as bool;
	}
}

[Native("stdlib::Arrays")]
public expand <T> T[] {
	[Native("sortWithComparator")]
	public extern sort(comparator as function(a as T, b as T) as int) as void;
	[Native("sortedWithComparator")]
	public extern sorted(comparator as function(a as T, b as T) as int) as T[];
	[Native("copy")]
	public extern copy() as T[];
	[Native("copyResize")]
	public extern copy(newSize as usize) as T[];
	[Native("copyTo")]
	public extern copyTo(target as T[], sourceOffset as usize, targetOffset as usize, length as usize) as void;
	
	public get first as T?
		=> this.isEmpty ? null : this[0];
	
	public get last as T?
		=> this.isEmpty ? null : this[$ - (1 as usize)];
	
	[Native("reverse")]
	public reverse() as void {
		for i in 0 .. length / 2 {
			var temp = this[i];
			this[i] = this[length - i - 1];
			this[length - i - 1] = temp;
		}
	}
	
	// TODO: fix compilation for these
	/*[Native("reversed")]
	public reversed() as T[] {
		return new T[](this, (i, value) => this[length - i - 1]);
	}*/
	
	[Native("mapValues")]
	public map<U>(projection as function(value as T`borrow) as U) as U[]
		=> new U[]<T>(this, projection);
	
	[Native("mapKeyValues")]
	public map<U>(projection as function(index as usize, value as T`borrow) as U) as U[]
		=> new U[]<T>(this, projection);
	
	[Native("filterValues")]
	public filter(predicate as function(value as T`borrow) as bool) as T[] {
		var values = new List<T>();
		for value in this
			if predicate(value)
				values.add(value);
		return values as T[];
	}
	
	[Native("filterKeyValues")]
	public filter(predicate as function(index as usize, value as T) as bool) as T[] {
		var values = new List<T>();
		for i, value in this
			if predicate(i, value)
				values.add(value);
		return values as T[];
	}
	
	public each(consumer as function(value as T) as void) as void {
		for value in this
			consumer(value);
	}
	
	public each(consumer as function(index as usize, value as T) as void) as void {
		for i, value in this
			consumer(i, value);
	}
	
	public contains(predicate as function(value as T) as bool) as bool {
		for value in this
			if predicate(value)
				return true;
		
		return false;
	}
	
	public contains(predicate as function(index as usize, value as T) as bool) as bool {
		for i, value in this
			if predicate(i, value)
				return true;
		
		return false;
	}
	
	public all(predicate as function(value as T) as bool) as bool {
		for value in this
			if !predicate(value)
				return false;
		
		return true;
	}
	
	public all(predicate as function(i as usize, value as T) as bool) as bool {
		for i, value in this
			if !predicate(i, value)
				return false;
		
		return true;
	}
	
	public first(predicate as function(value as T) as bool) as T? {
		for value in this
			if predicate(value)
				return value;
		
		return null;
	}
	
	public first(predicate as function(i as usize, value as T) as bool) as T? {
		for i, value in this
			if predicate(i, value)
				return value;
		
		return null;
	}
	
	public last(predicate as function(value as T) as bool) as T? {
		var i = length;
		while i > 0 {
			i--;
			if predicate(this[i])
				return this[i];
		}
		
		return null;
	}
	
	public last(predicate as function(index as usize, value as T) as bool) as T? {
		var i = length;
		while i > 0 {
			i--;
			if predicate(i, this[i])
				return this[i];
		}
		
		return null;
	}
	
	public count(predicate as function(value as T) as bool) as usize {
		var result = 0;
		for value in this
			if predicate(value)
				result++;
		return result;
	}
	
	public count(predicate as function(index as usize, value as T) as bool) as usize {
		var result = 0;
		for i, value in this
			if predicate(i, value)
				result++;
		return result;
	}
	
	public index<K>(key as function(value as T) as K) as T[K] {
		var result = new T[K];
		for value in this
			result[key(value)] = value;
		return result;
	}
}
