import { useState, useEffect } from 'react';
import axiosInstance from '../../axios';

const GetMedicalReports = () => {
    const [reports, setReports] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(15);
    const [totalItems, setTotalItems] = useState(0);

    const loadReports = async () => {
        setLoading(true);
        try {
            const res = await axiosInstance.get('/report', {
                params: {
                    page: page + 1,
                    size: rowsPerPage,
                    sort: 'receivedAt,desc'
                }
            });
            const data = res.data;
            setReports(data.item || []);
            setTotalItems(data.totalItems || 0);
        } catch (err) {
            console.error(err);
            setReports([]);
            setTotalItems(0);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadReports();
    }, [page, rowsPerPage]);

    return {
        reports,
        loading,
        page,
        setPage,
        rowsPerPage,
        setRowsPerPage,
        totalItems,
        refresh: loadReports
    };
};

export default GetMedicalReports;