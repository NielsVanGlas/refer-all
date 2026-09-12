import React, { forwardRef, useImperativeHandle } from 'react';
import {
    Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow,
    TablePagination, CircularProgress, Chip, Box, Typography, IconButton
} from '@mui/material';
import { Visibility, Delete } from '@mui/icons-material';
import GetMedicalReport from './GetMedicalReport';

const ShowMedicalReports = forwardRef(({ onView, onDelete, isAdmin }, ref) => {
    const {
        reports,
        loading,
        page,
        setPage,
        rowsPerPage,
        setRowsPerPage,
        totalItems,
        refresh
    } = GetMedicalReport();

    useImperativeHandle(ref, () => ({
        refetch: refresh
    }));

    const handlePageChange = (e, newPage) => setPage(newPage);
    const handleRowsPerPageChange = (e) => {
        setRowsPerPage(parseInt(e.target.value, 10));
        setPage(0);
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'NEW': return 'warning';
            case 'READ': return 'success';
            default: return 'default';
        }
    };

    const formatDate = (dateString) => {
        if (!dateString) return '-';
        return new Date(dateString).toLocaleString('it-IT', {
            day: '2-digit',
            month: 'short',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
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

    return (
        <Paper elevation={3}>
            <TableContainer>
                <Table stickyHeader size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell><strong>Titolo</strong></TableCell>
                            <TableCell><strong>Paziente</strong></TableCell>
                            <TableCell><strong>Dottore</strong></TableCell>
                            <TableCell><strong>Stato</strong></TableCell>
                            <TableCell><strong>Ricevuto il</strong></TableCell>
                            <TableCell align="center"><strong>Azioni</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {reports.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={6} align="center" sx={{ py: 6 }}>
                                    <Typography color="text.secondary" variant="h6">
                                        Nessun referto trovato
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            reports.map((report) => (
                                <TableRow key={report.fileId} hover>
                                    <TableCell>{report.title}</TableCell>
                                    <TableCell>{report.patient}</TableCell>
                                    <TableCell>{report.doctor}</TableCell>
                                    <TableCell>
                                        <Chip
                                            label={report.status}
                                            color={getStatusColor(report.status)}
                                            size="small"
                                        />
                                    </TableCell>
                                    <TableCell>{formatDate(report.receivedAt)}</TableCell>
                                    <TableCell align="center">
                                        <IconButton size="small" color="primary" onClick={() => onView(report.fileId)}>
                                            <Visibility fontSize="small" />
                                        </IconButton>
                                        {isAdmin && (
                                            <IconButton size="small" color="error" onClick={() => onDelete(report.fileId)}>
                                                <Delete fontSize="small" />
                                            </IconButton>
                                        )}
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
                rowsPerPageOptions={[10, 15, 25, 50]}
                labelRowsPerPage="Referti per pagina:"
                labelDisplayedRows={({ from, to, count }) => `${from}–${to} di ${count}`}
            />
        </Paper>
    );
});

ShowMedicalReports.displayName = 'ShowMedicalReports';
export default ShowMedicalReports;