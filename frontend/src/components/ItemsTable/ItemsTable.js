import { useState, useEffect } from "react";
import ItemCreateForm from "./ItemCreateForm";
import { useModal } from "../Modal";

function ItemRow({ data }) {
  return (
    <tr>
      <td>{data.name}</td>
      <td>{data.weightKg}</td>
      <td>{data.weirdnessLevel}</td>
      <td>{data.isSensitive}</td>
    </tr>
  );
}

function ItemsTable({ storageName }) {
  const [items, setItems] = useState([]);
  const [err, setErr] = useState(undefined);

  const [ modal, openModal, closeModal ] = useModal(<ItemCreateForm storageName={storageName} refreshItems={refreshItems} />);
  
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
        {modal}
        <table className="storage-table">
          <thead>
            <tr>
              <th>Nazwa</th>
              <th>Waga (kg)</th>
              <th>Poziom dziwności</th>
              <th>Delikatne</th>
            </tr>
          </thead>
          <tbody>
            {items.map((row, idx) => <ItemRow key={idx} data={row} />)}
          </tbody>
        </table>
        <button onClick={() => { openModal(); }}>Dodaj przedmiot</button>
      </>
    );
  } else {
    // render error
    return <p>{err}</p>
  }
}

export default ItemsTable;
