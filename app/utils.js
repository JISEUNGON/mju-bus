export function sortTimeTable(a, b) {
  return (
    parseInt(a.depart_at.substr(0, 2) + a.depart_at.substr(3, 4)) -
    parseInt(b.depart_at.substr(0, 2) + b.depart_at.substr(3, 4))
  );
}
