import java.util.ArrayList;
import java.util.Arrays;

public class SeparateChainingHashTable 
{
	// Starting out size for the table is prime because that is best.
	//  Prime numbers remove endless loops and help with having unique hashes.
	int m_nTableSize = 13;
	// Item counter to track the fullness of the table
	int itemCount = 0;
	// An Array of ArrayLists. Apparently it isn't a good idea to implement it this way.
	//   Where I come from, it is only a hack if it doesn't work.
	ArrayList<DataObject>[] m_ObjectArray; 
	
	public SeparateChainingHashTable()
	{
		m_ObjectArray = (ArrayList<DataObject>[])new ArrayList[m_nTableSize];
	}
	
	public SeparateChainingHashTable( int nTableSize )
	{
		m_nTableSize = nTableSize;
		m_ObjectArray = (ArrayList<DataObject>[])new ArrayList[m_nTableSize];
	}
	
	// Method to Expand the array. Slightly different than the Linear and Quad versions.
	//   Here we iterate over both the array of ArrayLists and the ArrayLists themselves.
	public void Expand() {
		// Resetting the item count for the new table.
		itemCount = 0;
		// Making a copy of the old table.
		ArrayList<DataObject>[] old = Arrays.copyOf(m_ObjectArray, m_ObjectArray.length);
		// More than doubling the size of the old table while keeping the size prime.
		m_nTableSize = (int) Utility.NextPrime(m_nTableSize * 2);
		m_ObjectArray = (ArrayList<DataObject>[])new ArrayList[m_nTableSize]; 
		
		// Iterating over the Array and the ArrayLists it contains. The extra conditions in the
		//   if statements keep the loops from breaking because of out of bounds issues.
		// Each item, j, is added to the list again.
		for (ArrayList<DataObject> i : old) {
			if (i != null && i.size() > 0) {
				for (DataObject j : i) {
					if (j != null && j.GetKey().length() > 0) {
						this.put(j);
					}
				}
			}
		}
	}
	
	// Method to add items to the table.
	public void put( DataObject objData ) {
		// Making the hash and index.
		long hash = Utility.HashFromString(objData.GetKey());
		int index = (int) (hash %= m_nTableSize);
		// Variable for the place in the ArrayList
		int i = 0;
		
		// If the index is empty, a new ArrayList must be made and the item must be added to that list.
		if (m_ObjectArray[index] == null) {
			ArrayList<DataObject> temp = new ArrayList<DataObject>();
			m_ObjectArray[index] = temp;
			m_ObjectArray[index].add(objData);
			itemCount++;
			return;
		}
		
		// If there is already an ArrayList at the index, search that ArrayList for the item and 
		//   overwrite it, if possible. 
		while (i < m_ObjectArray[index].size() && m_ObjectArray[index].get(i) != null) {
			if (objData.GetKey() == m_ObjectArray[index].get(i).GetKey()) {
				m_ObjectArray[index].set(i, objData); //.get(i)// = objData;
				return;
			}
			i++;
		}
		// If the item wasn't found, it is then added to the ArrayList and the item count is incremented
		m_ObjectArray[index].add(objData);
		itemCount++;
	}

	// Method to return the object associated with the key.
	public DataObject get( String strKey ) {
		// Finding the hash and index.
		long hash = Utility.HashFromString(strKey);
		int index = (int) (hash %= m_nTableSize);
		// Variable for the place in the ArrayList
		int i = 0;
		
		// If there is no list at the index, there are no items to be found.
		if (m_ObjectArray[index] == null) {
			return null;
		}
		
		// Search through the ArrayList at the index for the object matching the key.
		while (i < m_ObjectArray[index].size() && m_ObjectArray[index].get(i) != null) {
			if (m_ObjectArray[index].get(i).GetKey() == strKey) {
				return m_ObjectArray[index].get(i);
			}
			i++;
		}
		
		// End here if no match was found.
		return null;
	}
	
}
