interface FormattedDateProps {
  isoDate: string;
}

export const FormattedDate: React.FC<FormattedDateProps> = ({ isoDate }) => {
  const date = new Date(isoDate);

  const formattedDate = new Intl.DateTimeFormat('fr-FR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    timeZone: 'Europe/Paris',
  }).format(date);

  return <span>{formattedDate}</span>;
};
