import { useEffect, useState } from "react";

function Modal({ shown, open, close, children }) {
    if(shown) {
        return <div className="modal-root">
            <div className="modal">
                <div>{children}</div>
                <button className="modal-close-button" onClick={close}>Zamknij</button>
            </div>
        </div>
    } else {
        return <></>
    }
}

function useModal(children) {
    const [shown, setShown] = useState(false);

    const open = () => setShown(true);
    const close = () => setShown(false);

    useEffect(() => {
        document.addEventListener("keydown", (event) => {
            if(event.key === "Escape") {
                setShown(false);
            }
        });
    }, []);

    return [
        <Modal shown={shown} open={open} close={close}>{children || <></>}</Modal>,
        open,
        close
    ];
}

export default Modal;
export { useModal };