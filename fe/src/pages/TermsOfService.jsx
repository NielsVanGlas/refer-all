import React from 'react';
import { Dialog, DialogTitle, DialogContent, IconButton, Typography, Box, Container } from '@mui/material';
import { Close } from '@mui/icons-material';

const TermsOfService = ({ open, onClose }) => {
    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth scroll="paper">
            <DialogTitle sx={{ pr: 8 }}>
                <Typography variant="h5" component="div" fontWeight="bold">
                    Termini di Servizio - Template di Esempio
                </Typography>
                <IconButton
                    onClick={onClose}
                    sx={{ position: 'absolute', right: 8, top: 8 }}
                >
                    <Close />
                </IconButton>
            </DialogTitle>

            <DialogContent dividers>
                <Container maxWidth="md">
                    <Box sx={{ mb: 4 }}>
                        <Typography variant="subtitle1" color="text.secondary" paragraph>
                            Ultimo aggiornamento: 12 Setttembre 2026
                        </Typography>
                    </Box>

                    <Box sx={{ lineHeight: 1.8, '& p': { mb: 2 } }}>
                        <Typography paragraph>
                            Benvenuto sul nostro gestore di <strong>Referti medici</strong>. L'utilizzo della nostra piattaforma richiede l'accettazione dei Termini di Servizio sotto elencati.
                        </Typography>

                        <Typography variant="h5" gutterBottom mt={4}>1. Accettazione dei termini</Typography>
                        <Typography paragraph>
                            Registrandoti sulla piattaforma accetti integralmente i presenti Termini di Servizio.
                        </Typography>

                        <Typography variant="h5" gutterBottom mt={4}>2. Descrizione del servizio</Typography>
                        <Typography paragraph>
                            Referall è una piattaforma che simula la gestione di referti medici con la possibilità di essere integrata con altri sistemi e servizi anche in ambiti lavorativi.
                        </Typography>

                        <Typography variant="h5" gutterBottom mt={4}>3. Obblighi dell'utente</Typography>
                        <Typography paragraph>
                            L'utente si impegna a:
                            <ul>
                                <li>Fornire dati veritieri e aggiornati</li>
                                <li>Non condividere l'account con terzi</li>
                                <li>Segnalare immediatamente accessi non autorizzati</li>
                            </ul>
                        </Typography>

                        <Typography variant="h5" gutterBottom mt={4}>4. Responsabilità</Typography>
                        <Typography paragraph>
                            Non siamo responsabili per operazioni effettuate con credenziali corrette, anche in caso di uso improprio, da parte di terzi se non segnalato tempestivamente.
                        </Typography>

                        <Typography variant="h5" gutterBottom mt={4}>5. Modifiche ai termini</Typography>
                        <Typography paragraph>
                            Ci riserviamo il diritto di modificare i presenti termini in qualsiasi momento. Le modifiche saranno comunicate tempestivamente.
                        </Typography>

                        <Typography paragraph mt={6} color="text.secondary">
                            Per qualsiasi domanda contattateci.
                        </Typography>
                    </Box>
                </Container>
            </DialogContent>
        </Dialog>
    );
};

export default TermsOfService;