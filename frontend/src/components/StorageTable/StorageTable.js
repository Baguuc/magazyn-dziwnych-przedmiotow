import { useEffect, useState } from 'react';
import StorageCreateForm from './StorageCreateForm';
import { useModal } from '../Modal';

function StorageRow({ data }) {
    return <tr>
        <td>{data.name}</td>
        <td>{data.currentTotalWeight}kg / {data.maxTotalWeight}kg</td>
        <td>{data.currentItemCount} / {data.capacity}</td>
    </tr>
}
  
function StorageTable() {
    const [storages, setStorages] = useState([]);
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
    
    if(storages && !err) {
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
                        </tr>
                    </thead>
                    <tbody>
                        {storages.map((row, idx) => <StorageRow key={idx} data={row} />)}
                    </tbody>
                </table>
                <button className="storage-table-button" onClick={() => {
                    openModal();
                }}>Dodaj magazyn</button>
            </div>
        </>);
    } else {
        // render error
        return <p>{err}</p>
    }
}
  
export default StorageTable;