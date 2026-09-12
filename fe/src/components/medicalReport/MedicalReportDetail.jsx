import React, { useState, useEffect } from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions, Button, Typography,
    Box, CircularProgress, Alert, Chip, Grid, Divider
} from '@mui/material';
import { Close, Download, Delete } from '@mui/icons-material';
import axiosInstance from '../../axios';

const MedicalReportDetail = ({ open, onClose, reportId, isAdmin, onDeleted }) => {
    const [report, setReport] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [downloading, setDownloading] = useState(false);
    const [deleting, setDeleting] = useState(false);

    useEffect(() => {
        if (open && reportId) {
            setLoading(true);
            setError('');
            axiosInstance.get(`/report/${reportId}`)
                .then(res => setReport(res.data))
                .catch(err => {
                    setError('Impossibile caricare il referto');
                    console.error(err);
                })
                .finally(() => setLoading(false));
        } else {
            setReport(null);
        }
    }, [open, reportId]);

    const handleDownload = async () => {
        setDownloading(true);
        try {
            const res = await axiosInstance.get(`/report/download/${reportId}`, {
                responseType: 'blob'
            });

            const contentDisposition = res.headers['content-disposition'];
            let fileName = 'referto.pdf';
            if (contentDisposition) {
                const match = contentDisposition.match(/filename="?([^"]+)"?/);
                if (match) fileName = match[1];
            }

            const url = window.URL.createObjectURL(new Blob([res.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            alert('Errore durante il download');
            console.error(err);
        } finally {
            setDownloading(false);
        }
    };

    const handleDelete = async () => {
        if (!window.confirm('Sei sicuro di voler eliminare questo referto? L\'operazione è irreversibile.')) {
            return;
        }
        setDeleting(true);
        try {
            await axiosInstance.delete(`/report/${reportId}`);
            onDeleted?.();
            onClose();
        } catch (err) {
            alert('Errore durante l\'eliminazione');
        } finally {
            setDeleting(false);
        }
    };

    const formatDate = (dateString) => {
        if (!dateString) return '-';
        return new Date(dateString).toLocaleString('it-IT');
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                Dettaglio Referto
                <Button onClick={onClose} sx={{ position: 'absolute', right: 8, top: 8 }}>
                    <Close />
                </Button>
            </DialogTitle>
            <DialogContent dividers>
                {loading ? (
                    <Box textAlign="center" py={6}><CircularProgress /></Box>
                ) : error ? (
                    <Alert severity="error">{error}</Alert>
                ) : report ? (
                    <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                        <Typography variant="h6">{report.title}</Typography>

                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="caption" color="text.secondary">Paziente</Typography>
                                <Typography>{report.patient}</Typography>
                                <Typography variant="body2" color="text.secondary">
                                    CF: {report.patienTaxCode}
                                </Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="caption" color="text.secondary">Dottore</Typography>
                                <Typography>{report.doctor}</Typography>
                                <Typography variant="body2" color="text.secondary">
                                    N. iscrizione: {report.doctorLicenseNumber}
                                </Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="caption" color="text.secondary">Stato</Typography>
                                <Box mt={0.5}>
                                    <Chip
                                        label={report.status}
                                        color={report.status === 'NEW' ? 'warning' : 'success'}
                                        size="small"
                                    />
                                </Box>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <Typography variant="caption" color="text.secondary">Ricevuto il</Typography>
                                <Typography>{formatDate(report.receivedAt)}</Typography>
                            </Grid>
                        </Grid>

                        {report.notes && (
                            <>
                                <Divider />
                                <Box>
                                    <Typography variant="caption" color="text.secondary">Note</Typography>
                                    <Typography sx={{ whiteSpace: 'pre-wrap' }}>{report.notes}</Typography>
                                </Box>
                            </>
                        )}
                    </Box>
                ) : null}
            </DialogContent>
            <DialogActions>
                <Button
                    startIcon={downloading ? <CircularProgress size={18} /> : <Download />}
                    onClick={handleDownload}
                    disabled={downloading || loading}
                >
                    Scarica
                </Button>
                {isAdmin && (
                    <Button
                        color="error"
                        startIcon={deleting ? <CircularProgress size={18} /> : <Delete />}
                        onClick={handleDelete}
                        disabled={deleting || loading}
                    >
                        Elimina
                    </Button>
                )}
                <Button onClick={onClose}>Chiudi</Button>
            </DialogActions>
        </Dialog>
    );
};

export default MedicalReportDetail;