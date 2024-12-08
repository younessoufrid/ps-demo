ALTER TABLE a_order_artocc ADD COLUMN soumis boolean;
ALTER TABLE a_order_artocc ADD COLUMN state varchar;
UPDATE a_order_artocc SET soumis = false;
UPDATE a_order_artocc SET state = 'NOUVEAU';