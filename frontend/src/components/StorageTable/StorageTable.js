import { useEffect, useState } from 'react';
import StorageCreateForm from './StorageCreateForm';
import ItemsTable from "../ItemsTable/ItemsTable";
import { useModal } from '../Modal';

function StorageRow({ data, showItemsTable }) {
    return <tr>
        <td>{data.name}</td>
        <td>{data.currentTotalWeight}kg / {data.maxTotalWeight}kg</td>
        <td>{data.currentItemCount} / {data.capacity}</td>
        <td><button onClick={showItemsTable}>Zarządzaj zaopatrzeniem</button></td>
    </tr>
}
  
function StorageTable() {
    const [storages, setStorages] = useState([]);
    const [itemsTable, setItemsTable] = useState(undefined);
    const [err, setErr] = useState(undefined);

    const [ modal, openModal, closeModal ] = useModal(<StorageCreateForm refreshStorages={refreshStorages} />);
    
    useEffect(() => {
       refreshStorages();
    }, []);

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
    
    if(storages && !err && !itemsTable) {
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
                        {storages.map((row, idx) => <StorageRow key={idx} data={row} showItemsTable={() => {
                          setItemsTable(<ItemsTable storageName={row.name} />);
                        }} />)}
                    </tbody>
                </table>
                <button className="storage-table-button" onClick={() => {
                    openModal();
                }}>Dodaj magazyn</button>
            </div>
        </>);
    } else if(storages && !itemsTable) {
        // render error
        return <p>{err}</p>
    } else {
        return <>
          <button onClick={() => setItemsTable(undefined)} style={{ width: "100%" }}>Wróć</button>
          {itemsTable}
        </>;
    }
}
  
export default StorageTable;
