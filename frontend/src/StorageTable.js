import { useEffect, useState } from 'react';

function StorageRow(data) {
    return <tr>
      <td>{data.name}</td>
      <td>{data.currentTotalWeight}kg / {data.maxTotalWeight}kg</td>
      <td>{data.currentItemCount} / {data.capacity}</td>
    </tr>
  }
  
function StorageTable() {
    const [storages, setStorages] = useState([]);
    const [err, setErr] = useState(undefined);
    
    useEffect(() => {
        fetch("http://localhost:8080/storages")
        .then(response => {
            if(response.status !== 200) {
            setErr(response.body);
            return [];
            }
    
            return response.json();
        })
        .then(setStorages);
    }, []);
    
    if(storages && !err) {
        // render storages
        return <table>
        <thead>
            <th>Nazwa</th>
            <th>Łączna waga</th>
            <th>Ilość przedmiotów</th>
        </thead>
        <tbody>
            {storages.map(StorageRow)}
        </tbody>
        </table>
    } else {
        // render error
        return <p>{err}</p>
    }
}
  
export default StorageTable;