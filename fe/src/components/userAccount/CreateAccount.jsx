import React, { useState, useEffect } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { publicAxios } from '../../axios';
import {
    Box, Button, TextField, Typography, Alert, Container, MenuItem,
    FormControlLabel, Checkbox, Paper, Grid, Link, Chip, Stack,
    FormControl, InputLabel, Select, IconButton
} from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import PrivacyPolicy from '../../pages/PrivacyPolicy';
import TermsOfService from '../../pages/TermsOfService';

const CreateAccount = () => {

    const [formData, setFormData] = useState({
        password: '',
        firstName: '',
        lastName: '',
        gender: 'NA',
        bornDate: null,
        birthCity: '',
        birthProvinceCode: '',
        birthZipCode: '',
        taxCode: '',
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
        serviceTermsAndConditions: false,
        documentType: 'ID',
        documentId: '',
        patientProfile: {
            bloodType: 'OPLUS',
            allergies: '',
            chronicConditions: ''
        },
        doctorProfile: null
    });

    const [isDoctor, setIsDoctor] = useState(false);
    const [sameAsResidence, setSameAsResidence] = useState(true);
    const [selectedSpecializations, setSelectedSpecializations] = useState([]); // string[]
    const [newSpecialization, setNewSpecialization] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [privacyOpen, setPrivacyOpen] = useState(false);
    const [termsOpen, setTermsOpen] = useState(false);
    const navigate = useNavigate();

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

    // Toggle Doctor
    const handleDoctorToggle = (checked) => {
        setIsDoctor(checked);
        if (!checked) {
            setFormData(prev => ({ ...prev, doctorProfile: null }));
            setSelectedSpecializations([]);
            setNewSpecialization('');
        } else {
            setFormData(prev => ({
                ...prev,
                doctorProfile: {
                    licenseNumber: '',
                    specializations: []
                }
            }));
        }
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
        setSuccess('');

        const payload = {
            ...formData,
            home: sameAsResidence ? null : formData.home,
            doctorProfile: isDoctor
                ? {
                    licenseNumber: formData.doctorProfile?.licenseNumber || '',
                    specializations: selectedSpecializations.map(s => ({ specialization: s }))
                }
                : null
        };

        try {
            await publicAxios.post('/register', payload);
            setSuccess('Account creato! Controlla la tua email per verificare l\'account.');
            setTimeout(() => navigate('/login'), 3000);
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || 'Errore durante la registrazione.');
        }
    };

    return (
        <Container maxWidth={false} sx={{ width: '100%', minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', py: 4 }}>
            <Paper elevation={10} sx={{ p: { xs: 2.5, sm: 4 }, width: '100%', maxWidth: 1100, borderRadius: 3 }}>

                <Box sx={{ textAlign: 'center', mb: 3 }}>
                    <Typography variant="h4" fontWeight="bold">Registrazione</Typography>
                </Box>

                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                {success && <Alert severity="success" sx={{ mb: 2 }}>{success}</Alert>}

                <Box component="form" onSubmit={handleSubmit}>

                    <Typography variant="h6" gutterBottom>Anagrafica</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" required label="Nome" name="firstName"
                                value={formData.firstName} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" required label="Cognome" name="lastName"
                                value={formData.lastName} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" required label="Codice Fiscale" name="taxCode"
                                value={formData.taxCode} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField select fullWidth size="small" label="Sesso" name="gender"
                                value={formData.gender} onChange={handleChange}>
                                <MenuItem value="M">Maschio</MenuItem>
                                <MenuItem value="F">Femmina</MenuItem>
                                <MenuItem value="NA">Non specificato</MenuItem>
                            </TextField>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DesktopDatePicker
                                    label="Data di nascita"
                                    value={formData.bornDate ? dayjs(formData.bornDate) : null}
                                    onChange={(v) => setFormData(prev => ({
                                        ...prev,
                                        bornDate: v ? v.format('YYYY-MM-DD') : null
                                    }))}
                                    slotProps={{ textField: { fullWidth: true, size: 'small' } }}
                                    maxDate={dayjs().subtract(18, 'year')}
                                />
                            </LocalizationProvider>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Nato a" name="birthCity"
                                value={formData.birthCity} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={3}>
                            <TextField fullWidth size="small" label="Provincia" name="birthProvinceCode"
                                value={formData.birthProvinceCode} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={3}>
                            <TextField fullWidth size="small" label="CAP" name="birthZipCode"
                                value={formData.birthZipCode} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 4 }}>Contatti</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={8}>
                            <TextField fullWidth size="small" required type="email" label="E-mail" name="email"
                                value={formData.email} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={4}>
                            <TextField fullWidth size="small" label="Cellulare" name="mobile"
                                value={formData.mobile} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 4 }}>Residenza</Typography>
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

                    <Typography variant="h6" gutterBottom sx={{ mt: 4 }}>Documento di identità</Typography>
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
                            <TextField fullWidth size="small" required label="Numero documento" name="documentId"
                                value={formData.documentId} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField fullWidth size="small" required type="password" label="Password" name="password"
                                value={formData.password} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 4 }}>Profilo Paziente</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={4}>
                            <TextField
                                select
                                fullWidth
                                size="small"
                                required
                                label="Gruppo sanguigno"
                                value={formData.patientProfile.bloodType}
                                onChange={(e) => handleNestedChange('patientProfile', 'bloodType', e.target.value)}
                            >
                                <MenuItem value="OPLUS">0+</MenuItem>
                                <MenuItem value="OMINUX">0-</MenuItem>
                                <MenuItem value="APLUS">A+</MenuItem>
                                <MenuItem value="AMINUS">A-</MenuItem>
                                <MenuItem value="BPLUS">B+</MenuItem>
                                <MenuItem value="BMINUX">B-</MenuItem>
                                <MenuItem value="ABPLUS">AB+</MenuItem>
                                <MenuItem value="ABMINUS">AB-</MenuItem>
                            </TextField>
                        </Grid>
                        <Grid item xs={12} sm={4}>
                            <TextField
                                fullWidth
                                size="small"
                                label="Allergie"
                                value={formData.patientProfile.allergies}
                                onChange={(e) => handleNestedChange('patientProfile', 'allergies', e.target.value)}
                                placeholder="Es. penicillina, nichel..."
                            />
                        </Grid>
                        <Grid item xs={12} sm={4}>
                            <TextField
                                fullWidth
                                size="small"
                                label="Patologie croniche"
                                value={formData.patientProfile.chronicConditions}
                                onChange={(e) => handleNestedChange('patientProfile', 'chronicConditions', e.target.value)}
                                placeholder="Es. diabete, ipertensione..."
                            />
                        </Grid>
                    </Grid>

                    <Box sx={{ mt: 4 }}>
                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={isDoctor}
                                    onChange={(e) => handleDoctorToggle(e.target.checked)}
                                    color="primary"
                                />
                            }
                            label={<Typography fontWeight="medium">Sono un medico / voglio registrarmi anche come Dottore</Typography>}
                        />
                    </Box>

                    {isDoctor && (
                        <Box sx={{ mt: 2, p: 2, border: '1px solid', borderColor: 'divider', borderRadius: 2 }}>
                            <Typography variant="h6" gutterBottom>Profilo Dottore</Typography>

                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <TextField fullWidth size="small" required label="Numero di iscrizione all'Ordine"
                                        value={formData.doctorProfile?.licenseNumber || ''}
                                        onChange={(e) => handleNestedChange('doctorProfile', 'licenseNumber', e.target.value)} />
                                </Grid>
                            </Grid>

                            <Typography variant="subtitle1" sx={{ mt: 3, mb: 1 }}>Specializzazioni</Typography>

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

                    <Box sx={{ mt: 4 }}>
                        <FormControlLabel
                            required
                            control={
                                <Checkbox
                                    checked={formData.serviceTermsAndConditions}
                                    onChange={(e) => setFormData(prev => ({
                                        ...prev,
                                        serviceTermsAndConditions: e.target.checked
                                    }))}
                                    color="primary"
                                />
                            }
                            label={
                                <Typography variant="body2">
                                    Accetto i{' '}
                                    <Link component="button" type="button" variant="body2"
                                        onClick={(e) => { e.preventDefault(); setTermsOpen(true); }}
                                        sx={{ textDecoration: 'underline' }}>
                                        Termini di Servizio
                                    </Link>{' '}
                                    e l'{' '}
                                    <Link component="button" type="button" variant="body2"
                                        onClick={(e) => { e.preventDefault(); setPrivacyOpen(true); }}
                                        sx={{ textDecoration: 'underline' }}>
                                        Informativa Privacy
                                    </Link>
                                </Typography>
                            }
                        />

                        <FormControlLabel
                            control={
                                <Checkbox
                                    checked={formData.marketingConsensus}
                                    onChange={(e) => setFormData(prev => ({
                                        ...prev,
                                        marketingConsensus: e.target.checked
                                    }))}
                                    color="primary"
                                />
                            }
                            label="Voglio ricevere offerte promozionali"
                        />
                    </Box>

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        size="large"
                        disabled={!formData.serviceTermsAndConditions}
                        sx={{ mt: 3, mb: 2, py: 1.5 }}
                    >
                        Registrati
                    </Button>

                    <Typography align="center">
                        Hai già un account?{' '}
                        <RouterLink to="/login">Accedi</RouterLink>
                    </Typography>
                </Box>
            </Paper>

            <PrivacyPolicy open={privacyOpen} onClose={() => setPrivacyOpen(false)} />
            <TermsOfService open={termsOpen} onClose={() => setTermsOpen(false)} />
        </Container>
    );
};

export default CreateAccount;