-- Populate SEAT table with 50 seats per screen (5 rows x 10 seats)
-- Rows: A, B, C, D, E
-- Seats per row: 1-10

INSERT INTO SEAT (ROW_ID, SEAT_ROW_NUMBER, SCREEN_ID)
SELECT row_letter, seat_number, screen.SCREEN_ID
FROM SCREEN screen
CROSS JOIN (
    SELECT 'A' AS row_letter, 1 AS row_order UNION ALL
    SELECT 'B', 2 UNION ALL
    SELECT 'C', 3 UNION ALL
    SELECT 'D', 4 UNION ALL
    SELECT 'E', 5
) AS seat_rows
CROSS JOIN (
    SELECT 1 AS seat_number UNION ALL
    SELECT 2 UNION ALL
    SELECT 3 UNION ALL
    SELECT 4 UNION ALL
    SELECT 5 UNION ALL
    SELECT 6 UNION ALL
    SELECT 7 UNION ALL
    SELECT 8 UNION ALL
    SELECT 9 UNION ALL
    SELECT 10
) AS seats
ORDER BY screen.SCREEN_ID, seat_rows.row_order, seats.seat_number;
