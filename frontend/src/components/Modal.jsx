export default function Modal({ title = '알림', message, onClose, onConfirm, confirmText = '확인' }) {
  if (!message) return null;

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">{title}</div>
        <div className="modal-body">{message}</div>
        <div className="modal-footer">
          {onConfirm && (
            <button type="button" className="btn btn-ghost" onClick={onClose}>
              취소
            </button>
          )}
          <button
            type="button"
            className="btn btn-primary"
            onClick={onConfirm ? onConfirm : onClose}
            autoFocus
          >
            {onConfirm ? confirmText : '확인'}
          </button>
        </div>
      </div>
    </div>
  );
}
