import React, { useState } from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField,
    Box, Alert, CircularProgress, Typography
} from '@mui/material';
import { Close } from '@mui/icons-material';
import axiosInstance from '../../axios';

const CreateMedicalReport = ({ open, onClose, onSuccess }) => {
    const [formData, setFormData] = useState({
        patient: '',
        title: '',
        notes: ''
    });
    const [file, setFile] = useState(null);
    const [creating, setCreating] = useState(false);
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        const selected = e.target.files[0] || null;
        if (selected && selected.size > 5 * 1024 * 1024) {
            setError('Il file non può superare i 5MB');
            setFile(null);
            return;
        }
        setError('');
        setFile(selected);
    };

    const handleSubmit = async () => {
        if (!formData.patient.trim() || !formData.title.trim() || !file) {
            setError('Codice fiscale paziente, titolo e file sono obbligatori');
            return;
        }
        if (file.size > 5 * 1024 * 1024) {
            setError('Il file non può superare i 5MB');
            return;
        }

        setCreating(true);
        setError('');

        try {
            const payload = {
                patient: formData.patient.trim().toUpperCase(),
                title: formData.title.trim(),
                notes: formData.notes?.trim() || null
            };

            const body = new FormData();
            body.append('data', new Blob([JSON.stringify(payload)], { type: 'application/json' }));
            body.append('file', file);

            await axiosInstance.post('/report', body, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });

            onSuccess?.();
            handleClose();
        } catch (err) {
            setError(err.response?.data?.message || 'Errore durante la creazione del referto');
        } finally {
            setCreating(false);
        }
    };

    const handleClose = () => {
        if (creating) return;
        setFormData({ patient: '', title: '', notes: '' });
        setFile(null);
        setError('');
        onClose();
    };

    return (
        <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                Nuovo Referto
                <Button onClick={handleClose} sx={{ position: 'absolute', right: 8, top: 8 }} disabled={creating}>
                    <Close />
                </Button>
            </DialogTitle>
            <DialogContent dividers>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3, pt: 1 }}>
                    <TextField
                        label="Codice Fiscale Paziente"
                        name="patient"
                        value={formData.patient}
                        onChange={handleChange}
                        fullWidth
                        required
                        disabled={creating}
                        inputProps={{ style: { textTransform: 'uppercase' } }}
                        helperText="Inserisci il codice fiscale del paziente"
                    />

                    <TextField
                        label="Titolo"
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        fullWidth
                        required
                        disabled={creating}
                    />

                    <TextField
                        label="Note"
                        name="notes"
                        value={formData.notes}
                        onChange={handleChange}
                        fullWidth
                        multiline
                        rows={3}
                        disabled={creating}
                    />

                    <Box>
                        <Typography variant="body2" gutterBottom>File allegato *</Typography>
                        <Button variant="outlined" component="label" disabled={creating}>
                            {file ? file.name : 'Seleziona file'}
                            <input type="file" hidden onChange={handleFileChange} />
                        </Button>
                    </Box>

                    {error && <Alert severity="error">{error}</Alert>}
                </Box>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} disabled={creating}>Annulla</Button>
                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    disabled={creating}
                    startIcon={creating ? <CircularProgress size={20} /> : null}
                >
                    {creating ? 'Creazione...' : 'Crea Referto'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateMedicalReport;