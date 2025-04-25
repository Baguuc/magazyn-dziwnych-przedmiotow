import { useEffect, useState } from 'react';
import StorageCreateForm from './StorageCreateForm';
import ItemsTable from "../ItemsTable/ItemsTable";
import HeavyItemsTable from "../ItemsTable/HeavyItemsTable";
import { useModal } from '../Modal';

function StorageRow({ data, showItemsTable, showHeavyTable }) {
  function showMeanWeirdness() {
    fetch("http://localhost:8080/storages/master/items/weirdness")
      .then(response => response.json())
      .then(json => alert(json.mean))
  }  

  return <tr>
        <td>{data.name}</td>
        <td>{data.currentTotalWeight}kg / {data.maxTotalWeight}kg</td>
        <td>{data.currentItemCount} / {data.capacity}</td>
        <td>
          <button onClick={showItemsTable}>Zarządzaj zaopatrzeniem</button>
          <button onClick={showHeavyTable}>Zobacz cięzkie przedmioty</button>
          <button onClick={showMeanWeirdness}>Wylicz średnią dziwności</button>
        </td>
    </tr>
}
  
function StorageTable() {
    const [storages, setStorages] = useState([]);
    const [itemsTable, setItemsTable] = useState(undefined);
    const [heavyTable, setHeavyTable] = useState(undefined);
    const [err, setErr] = useState(undefined);

    const [ modal, openModal, closeModal ] = useModal(<StorageCreateForm refreshStorages={refreshStorages} />);
    
    useEffect(() => {
       refreshStorages();
    }, [itemsTable, heavyTable]);

    function refreshStorages() {
        fetch("http://localhost:8080/storages")
            .then(response => {
                if(response.status !== 200) {
                setErr(response.body);
                return [];
                }
        
                return response.json();
            })
            .then(setStorages);
    }
    
    if(storages && !itemsTable && !heavyTable && !err) {
        // render storages
        return (<>
            {modal}
            <div className="storage-table-root">
                <table className="storage-table">
                    <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Łączna waga</th>
                            <th>Ilość przedmiotów</th>
                            <th>Akcje</th>
                        </tr>
                    </thead>
                    <tbody>
                        {storages.map((row, idx) => {
                            return (
                              <StorageRow 
                                key={idx} 
                                data={row}
                                showItemsTable={() => {
                                  setItemsTable(<ItemsTable storageName={row.name} />);
                                }}
                                showHeavyTable={() => {
                                  setHeavyTable(<HeavyItemsTable storageName={row.name} />);
                                }}
                              />
                            );
                        })}
                    </tbody>
                </table>
                <button className="storage-table-button" onClick={() => {
                    openModal();
                }}>Dodaj magazyn</button>
            </div>
        </>);
    } else if(itemsTable) {
        return <div className="storage-table-root">
          <button onClick={() => setItemsTable(undefined)} style={{ width: "100%" }}>Wróć</button>
          {itemsTable}
        </div>;
    } else if(heavyTable) {
        return <div className="storage-table-root">
          <button onClick={() => setHeavyTable(undefined)} style={{ width: "100%" }}>Wróć</button>
          {heavyTable}
        </div>;
    } else if(err) {
        // render error
        return <p>{err}</p>
    }
}
  
export default StorageTable;
