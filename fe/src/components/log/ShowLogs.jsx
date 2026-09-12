import React, { useState, useEffect, forwardRef, useImperativeHandle } from 'react';
import axiosInstance from '../../axios';
import {
    Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow,
    TablePagination, CircularProgress, Alert, Chip, Box, Typography
} from '@mui/material';
import { History as HistoryIcon } from '@mui/icons-material';

const ShowLogs = forwardRef((props, ref) => {
    const [logs, setLogs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(15);
    const [totalItems, setTotalItems] = useState(0);

    const fetchLogs = async () => {
        setLoading(true);
        setError('');
        try {
            const res = await axiosInstance.get('/log', {
                params: {
                    page: page + 1,
                    size: rowsPerPage,
                    sort: 'createdAt,desc'
                }
            });

            const data = res.data;
            setLogs(data.item || []);
            setTotalItems(data.totalItems || 0);
        } catch (err) {
            setError('Impossibile caricare i log di accesso ai referti');
            console.error(err);
            setLogs([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchLogs();
    }, [page, rowsPerPage]);

    useImperativeHandle(ref, () => ({
        refetch: fetchLogs
    }));

    const handlePageChange = (e, newPage) => setPage(newPage);
    const handleRowsPerPageChange = (e) => {
        setRowsPerPage(parseInt(e.target.value, 10));
        setPage(0);
    };

    const getActionColor = (action) => {
        switch (action) {
            case 'UPLOAD': return 'success';
            case 'DOWNLOAD': return 'info';
            case 'INSPECT': return 'warning';
            case 'DELETE': return 'error';
            default: return 'default';
        }
    };

    if (loading) {
        return (
            <Paper elevation={3}>
                <Box sx={{ py: 8, textAlign: 'center' }}>
                    <CircularProgress />
                </Box>
            </Paper>
        );
    }

    if (error) {
        return (
            <Paper elevation={3}>
                <Alert severity="error" sx={{ m: 4 }}>{error}</Alert>
            </Paper>
        );
    }

    return (
        <Paper elevation={3}>
            <Box sx={{ p: 3, backgroundColor: 'error.main', color: 'white' }}>
                <Typography variant="h6" sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <HistoryIcon /> Log Accesso Referti
                </Typography>
                <Typography variant="body2">
                    Storico di upload, download, ispezioni e cancellazioni – Ordinati per data (più recente prima)
                </Typography>
            </Box>

            <TableContainer>
                <Table stickyHeader size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell><strong>Utente</strong></TableCell>
                            <TableCell><strong>Documento</strong></TableCell>
                            <TableCell><strong>Azione</strong></TableCell>
                            <TableCell><strong>Indirizzo IP</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {logs.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={4} align="center" sx={{ py: 6 }}>
                                    <Typography color="success.main" variant="h6">
                                        Nessun log registrato
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            logs.map((log, index) => (
                                <TableRow key={index} hover>
                                    <TableCell>
                                        <Typography variant="body2">
                                            {log.firstName} {log.lastName}
                                        </Typography>
                                    </TableCell>
                                    <TableCell>
                                        <Chip
                                            label={`${log.documentType === 'ID' ? 'CI' : 'Passaporto'} ${log.documentId || ''}`}
                                            size="small"
                                            variant="outlined"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Chip
                                            label={log.action}
                                            color={getActionColor(log.action)}
                                            size="small"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Typography variant="body2" sx={{ fontFamily: 'monospace', fontSize: '0.85rem' }}>
                                            {log.ipAddress || '-'}
                                        </Typography>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            <TablePagination
                component="div"
                count={totalItems}
                page={page}
                onPageChange={handlePageChange}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleRowsPerPageChange}
                rowsPerPageOptions={[10, 15, 25, 50, 100]}
                labelRowsPerPage="Log per pagina:"
                labelDisplayedRows={({ from, to, count }) => `${from}–${to} di ${count}`}
            />
        </Paper>
    );
});

ShowLogs.displayName = 'ShowLogs';
export default ShowLogs;