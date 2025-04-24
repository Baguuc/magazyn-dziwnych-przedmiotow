import { useState, useEffect } from "react";

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
    );
  } else {
    // render error
    return <p>{err}</p>
  }
}

export default ItemsTable;
