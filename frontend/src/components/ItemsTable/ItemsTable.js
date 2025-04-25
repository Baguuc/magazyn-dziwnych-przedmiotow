import { useState, useEffect } from "react";
import ItemCreateForm from "./ItemCreateForm";
import ItemDeleteForm from "./ItemDeleteForm";
import { useModal } from "../Modal";

function ItemRow({ data, deleteItem }) {
  return (
    <tr>
      <td>{data.name}</td>
      <td>{data.weightKg}</td>
      <td>{data.weirdnessLevel}</td>
      <td>{data.isSensitive}</td>
      <td>
        <button onClick={deleteItem}>Usuń</button>
      </td>
    </tr>
  );
}

function ItemsTable({ storageName }) {
  const [items, setItems] = useState([]);
  const [err, setErr] = useState(undefined);

  const [ createModal, openCreateModal, closeCreateModal ] = useModal(<ItemCreateForm storageName={storageName} refreshItems={refreshItems} />);
  const [ deleteModal, openDeleteModal, closeDeleteModal ] = useModal(<ItemDeleteForm storageName={storageName} refreshItems={refreshItems} />);
  
  useEffect(() => {
    refreshItems();
  }, []);

  function refreshItems() {
      fetch(`http://localhost:8080/storages/${storageName}`)
        .then(response => {
            if(response.status !== 200) {
              response.text().then(setErr)
              return [];
            }
    
            return response.json();
        })
        .then((json) => setItems(json.items));
  }

  if(items && !err) {
    return (
      <>
        {createModal}
        {deleteModal}
        <table className="storage-table">
          <thead>
            <tr>
              <th>Nazwa</th>
              <th>Waga (kg)</th>
              <th>Poziom dziwności</th>
              <th>Delikatne</th>
              <th>Akcje</th>
            </tr>
          </thead>
          <tbody>
            {items.map((row, idx) => <ItemRow key={idx} data={row} deleteItem={() => {
              fetch(`http://localhost:8080/storages/${storageName}/${row.name}`, { method: "DELETE" })
                .then(refreshItems);
            }} />)}
          </tbody>
        </table>
        <button onClick={() => { openCreateModal(); }}>Dodaj przedmiot</button>
        <button onClick={() => { openDeleteModal(); }}>Usuń przedmiot</button>
      </>
    );
  } else {
    // render error
    return <p>{err}</p>
  }
}

export default ItemsTable;
