//Email Service.js
 export async function sendEmail(id, eventType, btnEl = null) {
  if (btnEl) {
    btnEl.disabled = true;
    btnEl.dataset.originalText = btnEl.textContent;
    btnEl.textContent = 'Sending...';
  }
  showModal(`Sending email for ${eventType}...`);

  try {
    const res = await fetch(`${api}/customers/${id}/send-wish?occasion=${encodeURIComponent(eventType)}`, { method: 'POST' });
    const text = await res.text();
    const success = res.ok;
    updateModal(success ? `✅ Email sent successfully.` : `❌ Failed: ${text}`, success);

    if (success && btnEl) {
      btnEl.textContent = 'Sent ✔';
      disableSiblingButtons(btnEl);
    }
  } catch (err) {
    console.error('Email error:', err);
    updateModal('❌ Error sending email.', false);
  } finally {
    if (btnEl && btnEl.textContent !== 'Sent ✔') {
      btnEl.disabled = false;
      btnEl.textContent = btnEl.dataset.originalText || 'Send Email';
    }
  }
}

export async function sendSMS(id, eventType, btnEl = null) {
  if (btnEl) {
    btnEl.disabled = true;
    btnEl.dataset.originalText = btnEl.textContent;
    btnEl.textContent = 'Sending...';
  }
  showModal(`Sending SMS for ${eventType}...`);

  try {
    const res = await fetch(`${api}/customers/${id}/send-wish?occasion=${encodeURIComponent(eventType)}`, { method: 'POST' });
    const text = await res.text();
    const success = res.ok;
    updateModal(success ? `✅ SMS sent successfully.` : `❌ Failed: ${text}`, success);

    if (success && btnEl) {
      btnEl.textContent = 'Sent ✔';
      disableSiblingButtons(btnEl);
    }
  } catch (err) {
    console.error('SMS error:', err);
    updateModal('❌ Error sending SMS.', false);
  } finally {
    if (btnEl && btnEl.textContent !== 'Sent ✔') {
      btnEl.disabled = false;
      btnEl.textContent = btnEl.dataset.originalText || 'Send SMS';
    }
  }
}