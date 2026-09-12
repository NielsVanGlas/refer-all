import React, { useContext, useState, useRef } from 'react';
import { AuthenticationContext } from '../components/auth/AuthenticationContext';
import { useNavigate } from 'react-router-dom';
import {
    Container, Box, Typography, AppBar, Toolbar, IconButton, Avatar,
    Menu, MenuItem, Divider, ListItemIcon, Chip, CircularProgress
} from '@mui/material';
import { LocalHospital, Logout, History } from '@mui/icons-material';

import ShowLogs from '../components/log/ShowLogs';
import ShowErrors from '../components/error/ShowErrors';

const AdminPanel = () => {
    const { user, isLoading: authLoading, logout } = useContext(AuthenticationContext);
    const navigate = useNavigate();

    const [anchorEl, setAnchorEl] = useState(null);
    const showLogsRef = useRef();

    const handleMenuOpen = (e) => setAnchorEl(e.currentTarget);
    const handleMenuClose = () => setAnchorEl(null);

    React.useEffect(() => {
        if (!authLoading && (!user || user.role !== 'ADMIN')) {
            navigate('/');
        }
    }, [user, authLoading, navigate]);

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
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>
                        Pannello di Controllo
                    </Typography>
                    <Chip label="ADMIN" color="error" size="small" sx={{ mr: 2 }} />
                    <Typography variant="subtitle1" sx={{ mr: 2 }}>
                        {user.firstName} {user.lastName}
                    </Typography>
                    <IconButton onClick={handleMenuOpen}>
                        <Avatar sx={{ bgcolor: 'error.main' }}>{initials}</Avatar>
                    </IconButton>
                    <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                        <MenuItem onClick={() => { handleMenuClose(); navigate('/'); }}>
                            <ListItemIcon><LocalHospital fontSize="small" /></ListItemIcon>
                            Torna alla Home
                        </MenuItem>
                        <Divider />
                        <MenuItem onClick={logout}>
                            <ListItemIcon><Logout fontSize="small" /></ListItemIcon>
                            Esci
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 6 }}>
                <ShowLogs ref={showLogsRef} />
            </Container>

            <Container maxWidth="lg" sx={{ mt: 10, mb: 8 }}>
                <ShowErrors />
            </Container>
        </>
    );
};

export default AdminPanel;