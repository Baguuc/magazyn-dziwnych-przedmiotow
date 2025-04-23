import { useEffect, useState } from "react";

function Modal({ children, activatorId }) {
    const [shown, setShown] = useState(false);

    useEffect(() => {
        const element = document.getElementById(activatorId);

        if(!element) {
            return;
        }

        element.addEventListener("click", () => {
            setShown(true);
        });

        document.addEventListener("keydown", (event) => {
            if(event.key === "Escape") {
                setShown(false);
            }
        });
    }, []);

    if(shown) {
        return <div className="modal-root">
            <div className="modal">
                <div>{children}</div>
                <button className="modal-close-button" onClick={() => setShown(false)}>Zamknij</button>
            </div>
        </div>
    } else {
        return <></>
    }
}

export default Modal;