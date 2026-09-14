import React, { useState, useEffect } from 'react';
import axiosInstance from '../../axios';
import {
    Dialog, DialogContent, DialogActions, Box, Button, TextField, Typography,
    Alert, MenuItem, Select, InputLabel, FormControl, FormControlLabel,
    Checkbox, CircularProgress, Grid, Chip, Stack
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

const UpdateAccount = ({ open, onClose, user }) => {

    const [formData, setFormData] = useState({
        password: '',
        email: '',
        mobile: '',
        residence: {
            address: '',
            city: '',
            zipCode: '',
            provinceCode: '',
            countryCode: 'IT'
        },
        home: {
            address: '',
            city: '',
            zipCode: '',
            provinceCode: '',
            countryCode: 'IT'
        },
        marketingConsensus: false,
        documentType: 'ID',
        documentId: '',
        patientProfile: {
            allergies: '',
            chronicConditions: ''
        },
        specializations: []
    });

    const [sameAsResidence, setSameAsResidence] = useState(true);
    const [selectedSpecializations, setSelectedSpecializations] = useState([]);
    const [newSpecialization, setNewSpecialization] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [isDoctor, setIsDoctor] = useState(false);

    useEffect(() => {
        if (!user) return;

        const hasSameAddress = !user.home?.id || user.residence?.id === user.home?.id;

        setSameAsResidence(hasSameAddress);
        setIsDoctor(user.role === 'ADMIN');

        setFormData({
            password: '',
            email: user.email || '',
            mobile: user.mobile || '',
            residence: {
                address: user.residence?.address || '',
                city: user.residence?.city || '',
                zipCode: user.residence?.zipCode || '',
                provinceCode: user.residence?.provinceCode || '',
                countryCode: user.residence?.countryCode || 'IT'
            },
            home: {
                address: user.home?.address || user.residence?.address || '',
                city: user.home?.city || user.residence?.city || '',
                zipCode: user.home?.zipCode || user.residence?.zipCode || '',
                provinceCode: user.home?.provinceCode || user.residence?.provinceCode || '',
                countryCode: user.home?.countryCode || user.residence?.countryCode || 'IT'
            },
            marketingConsensus: user.marketingConsensus || false,
            documentType: user.documentType || 'ID',
            documentId: user.documentId || '',
            patientProfile: {
                allergies: user.patientProfile?.allergies || '',
                chronicConditions: user.patientProfile?.chronicConditions || ''
            },
            specializations: []
        });

        if (user.role === 'ADMIN' && user.doctorProfile?.specializations?.length) {
            setSelectedSpecializations(
                user.doctorProfile.specializations.map(s => s.specialization)
            );
        } else {
            setSelectedSpecializations([]);
        }
    }, [user, open]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleNestedChange = (section, field, value) => {
        setFormData(prev => ({
            ...prev,
            [section]: { ...prev[section], [field]: value }
        }));
    };

    const handleAddressChange = (type, field, value) => {
        setFormData(prev => ({
            ...prev,
            [type]: { ...prev[type], [field]: value }
        }));
    };

    const addSpecialization = () => {
        const value = newSpecialization.trim();
        if (!value) return;
        if (!selectedSpecializations.includes(value)) {
            setSelectedSpecializations(prev => [...prev, value]);
        }
        setNewSpecialization('');
    };

    const removeSpecialization = (spec) => {
        setSelectedSpecializations(prev => prev.filter(s => s !== spec));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        const payload = {
            ...formData,
            home: sameAsResidence ? null : formData.home,
            specializations: isDoctor
                ? selectedSpecializations.map(s => ({ specialization: s }))
                : null
        };

        if (!payload.password) {
            delete payload.password;
        }

        try {
            await axiosInstance.put('/user', payload);
            alert('Profilo aggiornato con successo!');
            onClose();
            window.location.reload();
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || 'Errore durante il salvataggio');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
            <Box sx={{ textAlign: 'center', pt: 3 }}>
                <Typography variant="h5" fontWeight="bold">Modifica Profilo</Typography>
            </Box>

            {error && <Alert severity="error" sx={{ mx: 3, mt: 2 }}>{error}</Alert>}

            <Box component="form" onSubmit={handleSubmit}>
                <DialogContent dividers>

                    <Typography variant="h6" gutterBottom>Contatti</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={8}>
                            <TextField fullWidth size="small" required label="E-mail" name="email"
                                value={formData.email} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={4}>
                            <TextField fullWidth size="small" label="Cellulare" name="mobile"
                                value={formData.mobile} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Residenza</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField fullWidth size="small" label="Indirizzo"
                                value={formData.residence.address}
                                onChange={(e) => handleAddressChange('residence', 'address', e.target.value)} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Città"
                                value={formData.residence.city}
                                onChange={(e) => handleAddressChange('residence', 'city', e.target.value)} />
                        </Grid>
                        <Grid item xs={12} sm={3}>
                            <TextField fullWidth size="small" label="CAP"
                                value={formData.residence.zipCode}
                                onChange={(e) => handleAddressChange('residence', 'zipCode', e.target.value)} />
                        </Grid>
                        <Grid item xs={12} sm={3}>
                            <TextField fullWidth size="small" label="Provincia"
                                value={formData.residence.provinceCode}
                                onChange={(e) => handleAddressChange('residence', 'provinceCode', e.target.value)} />
                        </Grid>
                        <Grid item xs={12} sm={3}>
                            <TextField fullWidth size="small" label="Stato"
                                value={formData.residence.countryCode}
                                onChange={(e) => handleAddressChange('residence', 'countryCode', e.target.value)} />
                        </Grid>
                    </Grid>

                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={!sameAsResidence}
                                onChange={(e) => setSameAsResidence(!e.target.checked)}
                                color="primary"
                            />
                        }
                        label="Domicilio diverso dalla residenza"
                        sx={{ mt: 2 }}
                    />

                    {!sameAsResidence && (
                        <>
                            <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Domicilio</Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <TextField fullWidth size="small" label="Indirizzo"
                                        value={formData.home.address}
                                        onChange={(e) => handleAddressChange('home', 'address', e.target.value)} />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField fullWidth size="small" label="Città"
                                        value={formData.home.city}
                                        onChange={(e) => handleAddressChange('home', 'city', e.target.value)} />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <TextField fullWidth size="small" label="CAP"
                                        value={formData.home.zipCode}
                                        onChange={(e) => handleAddressChange('home', 'zipCode', e.target.value)} />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <TextField fullWidth size="small" label="Provincia"
                                        value={formData.home.provinceCode}
                                        onChange={(e) => handleAddressChange('home', 'provinceCode', e.target.value)} />
                                </Grid>
                                <Grid item xs={12} sm={3}>
                                    <TextField fullWidth size="small" label="Stato"
                                        value={formData.home.countryCode}
                                        onChange={(e) => handleAddressChange('home', 'countryCode', e.target.value)} />
                                </Grid>
                            </Grid>
                        </>
                    )}

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Documento di identità</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth size="small">
                                <InputLabel>Tipo documento</InputLabel>
                                <Select name="documentType" value={formData.documentType}
                                    label="Tipo documento" onChange={handleChange}>
                                    <MenuItem value="ID">Carta d'identità</MenuItem>
                                    <MenuItem value="PASSPORT">Passaporto</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Numero documento" name="documentId"
                                value={formData.documentId} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <TextField
                        margin="normal"
                        fullWidth
                        size="small"
                        type="password"
                        label="Nuova password (lascia vuoto per non cambiare)"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                    />

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Profilo Paziente</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Allergie"
                                value={formData.patientProfile.allergies}
                                onChange={(e) => handleNestedChange('patientProfile', 'allergies', e.target.value)}
                                placeholder="Es. penicillina, nichel..." />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Patologie croniche"
                                value={formData.patientProfile.chronicConditions}
                                onChange={(e) => handleNestedChange('patientProfile', 'chronicConditions', e.target.value)}
                                placeholder="Es. diabete, ipertensione..." />
                        </Grid>
                    </Grid>

                    {isDoctor && (
                        <Box sx={{ mt: 3, p: 2, border: '1px solid', borderColor: 'divider', borderRadius: 2 }}>
                            <Typography variant="h6" gutterBottom>Specializzazioni</Typography>

                            <Stack direction="row" spacing={1} sx={{ mb: 2 }}>
                                <TextField
                                    fullWidth
                                    size="small"
                                    label="Nuova specializzazione"
                                    value={newSpecialization}
                                    onChange={(e) => setNewSpecialization(e.target.value)}
                                    onKeyDown={(e) => e.key === 'Enter' && (e.preventDefault(), addSpecialization())}
                                />
                                <Button variant="outlined" startIcon={<AddIcon />} onClick={addSpecialization}>
                                    Aggiungi
                                </Button>
                            </Stack>

                            <Stack direction="row" spacing={1} flexWrap="wrap" useFlexGap>
                                {selectedSpecializations.map((spec) => (
                                    <Chip
                                        key={spec}
                                        label={spec}
                                        onDelete={() => removeSpecialization(spec)}
                                        color="primary"
                                        variant="outlined"
                                    />
                                ))}
                            </Stack>
                        </Box>
                    )}

                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={formData.marketingConsensus}
                                onChange={(e) => setFormData(prev => ({
                                    ...prev,
                                    marketingConsensus: e.target.checked
                                }))}
                            />
                        }
                        label="Voglio ricevere offerte promozionali"
                        sx={{ mt: 3 }}
                    />
                </DialogContent>

                <DialogActions sx={{ p: 3 }}>
                    <Button onClick={onClose} disabled={loading}>Annulla</Button>
                    <Button type="submit" variant="contained" disabled={loading}>
                        {loading ? <CircularProgress size={24} /> : 'Salva Modifiche'}
                    </Button>
                </DialogActions>
            </Box>
        </Dialog>
    );
};

export default UpdateAccount;