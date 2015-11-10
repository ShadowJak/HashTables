import java.util.Arrays;

public class QuadraticProbeHashTable 
{
	// Same as the Linear Probe table, starting with a Prime size.
	//   Prime numbers remove endless loops and help with having unique hashes.
	int m_nTableSize = 13;
	// The array of objects that make up the table
	DataObject[] m_ObjectArray;
	// The item counter
	int itemCount = 0;
	
	public QuadraticProbeHashTable()
	{
		m_ObjectArray = new DataObject[m_nTableSize];
	}
	
	public QuadraticProbeHashTable( int nTableSize )
	{
		m_nTableSize = nTableSize;
		m_ObjectArray = new DataObject[m_nTableSize];
	}
	
	// Method to Expand the table. Pretty much identical to the linear probe version.
	public void Expand() {
		// Resetting the item counter because the table will be remade
		itemCount = 0;
		// Making a copy of the table before it is remade
		DataObject[] old = Arrays.copyOf(m_ObjectArray, m_ObjectArray.length);
		// More than doubling the size of the array while keeping the size prime
		m_nTableSize = (int) Utility.NextPrime(m_nTableSize * 2);
		// Setting the variable to a new array with the larger size.
		m_ObjectArray = new DataObject[m_nTableSize];
		
		// For each object in the old array, add that object to the new array with a new hash.
		for (DataObject x : old) {
			if (x != null && x.GetKey().length() > 0) {
				this.put(x);
			}
		}
	}
	
	// Method to add items to the table. Similar to the linear probe version but with better probing.
	public void put( DataObject objData ) {
		// Generating the hash and index
		long hash = Utility.HashFromString(objData.GetKey());
		int index = (int) (hash %= m_nTableSize);
		// This number will be raised to the power of 2 and added to the index when searching.
		//   This results in less grouping of items which allows for a faster table.
		int e = 1;
		
		// If the index isn't empty, continue searching for an empty index. If a duplicate is found,
		//   overwrite the old information.
		while (m_ObjectArray[index] != null) {
			if (objData.GetKey() == m_ObjectArray[index].GetKey()) {
				m_ObjectArray[index] = objData;
				return;
			}
			// here we increment it with a quadratic equation. 1^2 then 2^2, then 3^2, etc.
			index = index + (e * e);
			e++;
			index %= m_nTableSize;
		}
		
		// If this point is reached, an empty spot has been found.
		// The item is added and the counter incremented.
		m_ObjectArray[index] = objData;
		itemCount++;
		
		// Checking to see if there are too many items in the table. If so, Expand it.
		if (itemCount > (m_nTableSize / 2)) {
			Expand();
		}
	}

	// Method to return the object associated with the string.
	// Per the instructions and guidance from Michael the TA, there is no other value to return.
	//   Therefore the object itself is returned. If not found, null is returned.
	public DataObject get( String strKey ) {
		long hash = Utility.HashFromString(strKey);
		int index = (int) (hash %= m_nTableSize);
		int e = 1;
		while (m_ObjectArray[index] != null) {
			if (m_ObjectArray[index].GetKey() == strKey) {
				return m_ObjectArray[index];
			}
			// The search here works the same as the search in the put method. 
			index = index + (e * e);
			e++;
			index %= m_nTableSize;
		}
		
		// Return null if nothing is matched.
		return null;
	}
	
}
