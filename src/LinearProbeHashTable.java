import java.util.Arrays;

public class LinearProbeHashTable 
{
	// Starting out size for the table is prime because that is best.
	//  Prime numbers remove endless loops and help with having unique hashes.
	int m_nTableSize = 13;
	// Array of DataObjects that will be added to
	DataObject[] m_ObjectArray;
	// Counter for the number of items in the table. Will be used to determine when to expand.
	int itemCount = 0;
	
	public LinearProbeHashTable()
	{
		m_ObjectArray = new DataObject[m_nTableSize];
	}
	
	public LinearProbeHashTable( int nTableSize )
	{
		m_nTableSize = nTableSize;
		m_ObjectArray = new DataObject[m_nTableSize];
	}
	
	// Method to more than double the size of the table to keep it fast.
	public void Expand() {
		// Resetting the itemCount. This will be incremented back up in put();
		itemCount = 0;
		// Making a copy of the old table before it is remade.
		DataObject[] old = Arrays.copyOf(m_ObjectArray, m_ObjectArray.length);
		// More than doubling the size of the table while making sure it stays prime.
		m_nTableSize = (int) Utility.NextPrime(m_nTableSize * 2);
		// Reseting the table so it can be remade at the larger size.
		m_ObjectArray = new DataObject[m_nTableSize];
		
		// For each item in the old table, re-put it into new table with an updated hash
		for (DataObject x : old) {
			if (x != null && x.GetKey().length() > 0) {
				this.put(x);
			}
		}
	}
	
	// Method to put items into the table.
	public void put( DataObject objData ) {
		// Generating the hash value. 
		long hash = Utility.HashFromString(objData.GetKey());
		// Taking the mod of the hash and turning it into an int to be used as the index
		int index = (int) (hash %= m_nTableSize);
		
		// As long as an empty index isn't found, linearly search for an empty spot
		while (m_ObjectArray[index] != null) {
			// If the key already exists, copy the new info over the old and end the method.
			if (objData.GetKey() == m_ObjectArray[index].GetKey()) {
				m_ObjectArray[index] = objData;
				return;
			}
			// Incrementing the index and taking its modulus to stay in bounds
			index++;
			index %= m_nTableSize;
		}
		
		// If we reach this point, an empty spot has been found and the object is added to it.
		m_ObjectArray[index] = objData;
		// Incrementing the item counter to keep track of the number of items added.
		itemCount++;
		
		// If there are too many items, expand the table.
		if (itemCount > (m_nTableSize / 2)) {
			Expand();
		}
	}

	// Method to return the object associated with the string.
	// Per the instructions and guidance from Michael the TA, there is no other value to return.
	//   Therefore the object itself is returned. If not found, null is returned.
	public DataObject get( String strKey ) {
		// Getting the hash and index from the string.
		long hash = Utility.HashFromString(strKey);
		int index = (int) (hash %= m_nTableSize);
		
		// If the index isn't empty, check it against the key and keep searching linearly.
		while (m_ObjectArray[index] != null) {
			if (m_ObjectArray[index].GetKey() == strKey) {
				return m_ObjectArray[index];
			}
			index++;
			index %= m_nTableSize;
		}
		
		// If this point is reached, no match was found.
		return null;
	}
	
}
