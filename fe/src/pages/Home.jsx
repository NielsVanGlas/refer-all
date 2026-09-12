import React, { useContext, useState, useRef } from 'react';
import { AuthenticationContext } from '../components/auth/AuthenticationContext';
import {
    Container, Box, Typography, AppBar, Toolbar, IconButton, Avatar,
    Menu, MenuItem, Divider, ListItemIcon, Button, CircularProgress
} from '@mui/material';
import {
    LocalHospital, Logout, Edit, AdminPanelSettings,
    Security, Description, Add
} from '@mui/icons-material';

import UpdateAccount from '../components/userAccount/UpdateAccount';
import DeleteAccount from '../components/userAccount/DeleteAccount';
import PrivacyPolicy from './PrivacyPolicy';
import TermsOfService from './TermsOfService';
import ShowMedicalReport from '../components/medicalReport/ShowMedicalReport';
import CreateMedicalReport from '../components/medicalReport/CreateMedicalReport';
import MedicalReportDetail from '../components/medicalReport/MedicalReportDetail';

const Home = () => {
    const { user, isLoading: authLoading, logout } = useContext(AuthenticationContext);

    const [anchorEl, setAnchorEl] = useState(null);
    const [editOpen, setEditOpen] = useState(false);
    const [privacyOpen, setPrivacyOpen] = useState(false);
    const [termsOpen, setTermsOpen] = useState(false);
    const [createOpen, setCreateOpen] = useState(false);
    const [detailId, setDetailId] = useState(null);

    const showReportsRef = useRef();

    const handleMenuOpen = (e) => setAnchorEl(e.currentTarget);
    const handleMenuClose = () => setAnchorEl(null);

    const isAdmin = user?.role === 'ADMIN';

    const handleDeleteFromList = async (id) => {
        if (!window.confirm('Sei sicuro di voler eliminare questo referto?')) return;
        try {
            const axiosInstance = (await import('../axios')).default;
            await axiosInstance.delete(`/report/${id}`);
            showReportsRef.current?.refetch?.();
        } catch (err) {
            alert('Errore durante l\'eliminazione');
        }
    };

    if (authLoading || !user) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 10 }}>
                <CircularProgress />
            </Box>
        );
    }

    const initials = `${user.firstName?.[0] || ''}${user.lastName?.[0] || ''}`;

    return (
        <>
            <AppBar position="static" color="transparent" elevation={0} sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Toolbar>
                    <LocalHospital sx={{ mr: 2, fontSize: 30 }} />
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>Referall</Typography>
                    <Typography variant="subtitle1" sx={{ mr: 2 }}>
                        {user.firstName} {user.lastName}
                    </Typography>
                    <IconButton onClick={handleMenuOpen}>
                        <Avatar sx={{ bgcolor: 'primary.main' }}>{initials}</Avatar>
                    </IconButton>
                    <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                        <MenuItem onClick={() => { setEditOpen(true); handleMenuClose(); }}>
                            <ListItemIcon><Edit fontSize="small" /></ListItemIcon>
                            Modifica Profilo
                        </MenuItem>
                        {isAdmin && (
                            <>
                                <Divider />
                                <MenuItem onClick={() => { handleMenuClose(); window.location.href = '/admin'; }}>
                                    <ListItemIcon><AdminPanelSettings fontSize="small" /></ListItemIcon>
                                    Pannello Admin
                                </MenuItem>
                            </>
                        )}
                        <Divider />
                        <MenuItem onClick={() => { setPrivacyOpen(true); handleMenuClose(); }}>
                            <ListItemIcon><Security fontSize="small" /></ListItemIcon>
                            Privacy
                        </MenuItem>
                        <MenuItem onClick={() => { setTermsOpen(true); handleMenuClose(); }}>
                            <ListItemIcon><Description fontSize="small" /></ListItemIcon>
                            Termini di Servizio
                        </MenuItem>
                        <Divider />
                        <MenuItem onClick={logout}>
                            <ListItemIcon><Logout fontSize="small" /></ListItemIcon>
                            Esci
                        </MenuItem>
                        <DeleteAccount />
                    </Menu>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 6, mb: 8 }}>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
                    <Box>
                        <Typography variant="h4" gutterBottom>
                            Ciao, {user.firstName}!
                        </Typography>
                        <Typography color="text.secondary">
                            I tuoi referti medici
                        </Typography>
                    </Box>
                    {isAdmin && (
                        <Button
                            variant="contained"
                            startIcon={<Add />}
                            onClick={() => setCreateOpen(true)}
                        >
                            Nuovo Referto
                        </Button>
                    )}
                </Box>

                <ShowMedicalReport
                    ref={showReportsRef}
                    isAdmin={isAdmin}
                    onView={setDetailId}
                    onDelete={handleDeleteFromList}
                />
            </Container>

            <CreateMedicalReport
                open={createOpen}
                onClose={() => setCreateOpen(false)}
                onSuccess={() => showReportsRef.current?.refetch?.()}
            />

            <MedicalReportDetail
                open={!!detailId}
                onClose={() => setDetailId(null)}
                reportId={detailId}
                isAdmin={isAdmin}
                onDeleted={() => showReportsRef.current?.refetch?.()}
            />

            <UpdateAccount open={editOpen} onClose={() => setEditOpen(false)} user={user} />
            <PrivacyPolicy open={privacyOpen} onClose={() => setPrivacyOpen(false)} />
            <TermsOfService open={termsOpen} onClose={() => setTermsOpen(false)} />
        </>
    );
};

export default Home;